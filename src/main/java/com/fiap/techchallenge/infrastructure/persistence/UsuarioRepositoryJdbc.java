package com.fiap.techchallenge.infrastructure.persistence;

import com.fiap.techchallenge.domain.entities.Usuario;
import com.fiap.techchallenge.domain.enums.TipoUsuario;
import com.fiap.techchallenge.domain.repositories.UsuarioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryJdbc implements UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UsuarioRowMapper rowMapper = new UsuarioRowMapper();

    public UsuarioRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            return insert(usuario);
        } else {
            return update(usuario);
        }
    }

    private Usuario insert(Usuario usuario) {
        String sql = """
                INSERT INTO tb_usuarios (nm_usuario, ds_email, ds_login, ds_senha, nr_cpf,
                    tp_usuario, ds_endereco_rua, nr_endereco_numero, ds_endereco_cidade,
                    nr_endereco_cep, dt_criacao, dt_atualizacao)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getLogin());
            ps.setString(4, usuario.getSenha());
            ps.setString(5, usuario.getCpf());
            ps.setString(6, usuario.getTipoUsuario().name());
            ps.setString(7, usuario.getEnderecoRua());
            ps.setString(8, usuario.getEnderecoNumero());
            ps.setString(9, usuario.getEnderecoCidade());
            ps.setString(10, usuario.getEnderecoCep());
            ps.setTimestamp(11, Timestamp.valueOf(now));
            ps.setTimestamp(12, Timestamp.valueOf(now));
            return ps;
        }, keyHolder);

        usuario.setId(keyHolder.getKey().longValue());
        usuario.setDataCriacao(now);
        usuario.setDataAtualizacao(now);
        return usuario;
    }

    private Usuario update(Usuario usuario) {
        String sql = """
                UPDATE tb_usuarios SET
                    nm_usuario = ?, ds_email = ?, ds_login = ?, ds_senha = ?, nr_cpf = ?,
                    tp_usuario = ?, ds_endereco_rua = ?, nr_endereco_numero = ?,
                    ds_endereco_cidade = ?, nr_endereco_cep = ?, dt_atualizacao = ?
                WHERE id_usuario = ?
                """;

        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update(sql,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getCpf(),
                usuario.getTipoUsuario().name(),
                usuario.getEnderecoRua(),
                usuario.getEnderecoNumero(),
                usuario.getEnderecoCidade(),
                usuario.getEnderecoCep(),
                Timestamp.valueOf(now),
                usuario.getId());

        usuario.setDataAtualizacao(now);
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        String sql = "SELECT * FROM tb_usuarios WHERE id_usuario = ?";
        List<Usuario> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM tb_usuarios ORDER BY id_usuario";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        String sql = "SELECT * FROM tb_usuarios WHERE ds_email = ?";
        List<Usuario> results = jdbcTemplate.query(sql, rowMapper, email);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        String sql = "SELECT * FROM tb_usuarios WHERE ds_login = ?";
        List<Usuario> results = jdbcTemplate.query(sql, rowMapper, login);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Usuario> findByNomeContainingIgnoreCase(String nome) {
        String sql = "SELECT * FROM tb_usuarios WHERE LOWER(nm_usuario) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, rowMapper, "%" + nome + "%");
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        String sql = "SELECT * FROM tb_usuarios WHERE nr_cpf = ?";
        List<Usuario> results = jdbcTemplate.query(sql, rowMapper, cpf);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario) {
        String sql = "SELECT * FROM tb_usuarios WHERE tp_usuario = ?";
        return jdbcTemplate.query(sql, rowMapper, tipoUsuario.name());
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM tb_usuarios WHERE ds_email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByLogin(String login) {
        String sql = "SELECT COUNT(*) FROM tb_usuarios WHERE ds_login = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, login);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCpf(String cpf) {
        String sql = "SELECT COUNT(*) FROM tb_usuarios WHERE nr_cpf = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM tb_usuarios WHERE id_usuario = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM tb_usuarios WHERE id_usuario = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class UsuarioRowMapper implements RowMapper<Usuario> {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("id_usuario"));
            usuario.setNome(rs.getString("nm_usuario"));
            usuario.setEmail(rs.getString("ds_email"));
            usuario.setLogin(rs.getString("ds_login"));
            usuario.setSenha(rs.getString("ds_senha"));
            usuario.setCpf(rs.getString("nr_cpf"));
            usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tp_usuario")));
            usuario.setEnderecoRua(rs.getString("ds_endereco_rua"));
            usuario.setEnderecoNumero(rs.getString("nr_endereco_numero"));
            usuario.setEnderecoCidade(rs.getString("ds_endereco_cidade"));
            usuario.setEnderecoCep(rs.getString("nr_endereco_cep"));

            Timestamp dtCriacao = rs.getTimestamp("dt_criacao");
            if (dtCriacao != null) {
                usuario.setDataCriacao(dtCriacao.toLocalDateTime());
            }

            Timestamp dtAtualizacao = rs.getTimestamp("dt_atualizacao");
            if (dtAtualizacao != null) {
                usuario.setDataAtualizacao(dtAtualizacao.toLocalDateTime());
            }

            return usuario;
        }
    }
}
