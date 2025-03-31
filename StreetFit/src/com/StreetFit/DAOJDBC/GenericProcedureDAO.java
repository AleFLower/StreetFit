package com.StreetFit.DAOJDBC;

import java.sql.SQLException;

import com.StreetFit.exception.DAOException;


public interface GenericProcedureDAO<P> {

    P execute(Object... params) throws DAOException, SQLException;

}
