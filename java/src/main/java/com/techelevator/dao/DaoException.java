package com.techelevator.dao;

import org.springframework.jdbc.CannotGetJdbcConnectionException;

public class DaoException extends RuntimeException {
    public DaoException() {
        super();
    }
    public DaoException(String message) {
        super(message);
    }
    public DaoException(String message, Exception cause) {
        super(message, cause);
    }
}

//catch (CannotGetJdbcConnectionException e) {
//        throw new DaoException("Unable to connect to server or database", e);
//        } catch (BadSqlGrammarException e) {
//        throw new DaoException("SQL syntax error", e);
//        } catch (DataIntegrityViolationException e) {
//        throw new DaoException("Data integrity violation", e);
//        }