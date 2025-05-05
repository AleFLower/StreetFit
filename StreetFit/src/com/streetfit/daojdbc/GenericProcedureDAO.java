package com.streetfit.daojdbc;

import java.sql.SQLException;

import com.streetfit.exception.DAOException;


public interface GenericProcedureDAO<P> {

    P execute(Object... params) throws DAOException, SQLException;

}
