package com.streetfit.daojdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.streetfit.dao.LoginDAO;
import com.streetfit.exception.DAOException;
import com.streetfit.model.Credentials;
import com.streetfit.model.Role;

public class LoginProcedureDAO implements LoginDAO {

    @Override
    public Credentials getCredentials(String username, String password) throws DAOException {
        int role;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call login(?,?,?)}");
            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.NUMERIC);
            cs.executeQuery();
            role = cs.getInt(3);
        } catch (SQLException e) {
            throw new DAOException("Login error: " + e.getMessage());
        }

        return new Credentials(username, password, Role.fromInt(role));
    }
}
