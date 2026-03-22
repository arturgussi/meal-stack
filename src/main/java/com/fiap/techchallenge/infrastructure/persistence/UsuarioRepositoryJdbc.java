package com.fiap.techchallenge.infrastructure.persistence;

import com.fiap.techchallenge.domain.entities.User;
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
    public User save(User user) {
        if (user.getId() == null) {
            return insert(user);
        } else {
            return update(user);
        }
    }

    private User insert(User user) {
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
            ps.setString(1, user.getNome());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getSenha());
            ps.setString(5, user.getCpf());
            ps.setString(6, user.getTipoUsuario().name());
            ps.setString(7, user.getEnderecoRua());
            ps.setString(8, user.getEnderecoNumero());
            ps.setString(9, user.getEnderecoCidade());
            ps.setString(10, user.getEnderecoCep());
            ps.setTimestamp(11, Timestamp.valueOf(now));
            ps.setTimestamp(12, Timestamp.valueOf(now));
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        user.setDataCriacao(now);
        user.setDataAtualizacao(now);
        return user;
    }

    private User update(User user) {
        String sql = """
                UPDATE tb_usuarios SET
                    nm_usuario = ?, ds_email = ?, ds_login = ?, ds_senha = ?, nr_cpf = ?,
                    tp_usuario = ?, ds_endereco_rua = ?, nr_endereco_numero = ?,
                    ds_endereco_cidade = ?, nr_endereco_cep = ?, dt_atualizacao = ?
                WHERE id_usuario = ?
                """;

        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update(sql,
                user.getNome(),
                user.getEmail(),
                user.getLogin(),
                user.getSenha(),
                user.getCpf(),
                user.getTipoUsuario().name(),
                user.getEnderecoRua(),
                user.getEnderecoNumero(),
                user.getEnderecoCidade(),
                user.getEnderecoCep(),
                Timestamp.valueOf(now),
                user.getId());

        user.setDataAtualizacao(now);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM tb_usuarios WHERE id_usuario = ?";
        List<User> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM tb_usuarios ORDER BY id_usuario";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM tb_usuarios WHERE ds_email = ?";
        List<User> results = jdbcTemplate.query(sql, rowMapper, email);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String sql = "SELECT * FROM tb_usuarios WHERE ds_login = ?";
        List<User> results = jdbcTemplate.query(sql, rowMapper, login);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<User> findByNomeContainingIgnoreCase(String nome) {
        String sql = "SELECT * FROM tb_usuarios WHERE LOWER(nm_usuario) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, rowMapper, "%" + nome + "%");
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        String sql = "SELECT * FROM tb_usuarios WHERE nr_cpf = ?";
        List<User> results = jdbcTemplate.query(sql, rowMapper, cpf);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<User> findByTipoUsuario(TipoUsuario tipoUsuario) {
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

    private static class UsuarioRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id_usuario"));
            user.setNome(rs.getString("nm_usuario"));
            user.setEmail(rs.getString("ds_email"));
            user.setLogin(rs.getString("ds_login"));
            user.setSenha(rs.getString("ds_senha"));
            user.setCpf(rs.getString("nr_cpf"));
            user.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tp_usuario")));
            user.setEnderecoRua(rs.getString("ds_endereco_rua"));
            user.setEnderecoNumero(rs.getString("nr_endereco_numero"));
            user.setEnderecoCidade(rs.getString("ds_endereco_cidade"));
            user.setEnderecoCep(rs.getString("nr_endereco_cep"));

            Timestamp dtCriacao = rs.getTimestamp("dt_criacao");
            if (dtCriacao != null) {
                user.setDataCriacao(dtCriacao.toLocalDateTime());
            }

            Timestamp dtAtualizacao = rs.getTimestamp("dt_atualizacao");
            if (dtAtualizacao != null) {
                user.setDataAtualizacao(dtAtualizacao.toLocalDateTime());
            }

            return user;
        }
    }
}
