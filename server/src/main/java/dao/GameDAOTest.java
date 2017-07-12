package dao;

import java.sql.Connection;

/**
 *  <h1>Game Table Test Class</h1>
 *  Provides tests for game table capabilities.
 *
 *  @author     Nathan Finch
 *  @since      7/12/2017
 */
public class GameDAOTest
{

    //Data

    private static final String DB_TO_USE = "server/db/tstdb.sqlite";

    private MasterDAO.GameDAO mGameAccess;
    private Connection testConnection;

}
