package support;


import app.config.DatabaseConfig;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DriverManagerDataSource implements DataSource{

    private final String url;
    private final String username;
    private final String password;

    public DriverManagerDataSource(DatabaseConfig config) {
        this.url = config.url();
        this.username = config.username();
        this.password = config.password();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Method not supported");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("Method not supported");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("Method not supported");
    }
}
