package net.plaut.bprtab.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.plaut.bprtab.dao.BpaUserTableDao;
import net.plaut.bprtab.dao.BpaUserTableRecord;
import net.plaut.bprtab.dao.condition.BpaUserSrcCond;
import net.plaut.bprtab.object.AddUserDto;
import net.plaut.bprtab.object.UpdateUserDto;
import net.plaut.bprtab.util.SystemInformation;
import net.plaut.dbutil.db.DbConnection;

public class UserLogic {

	private static UserLogic instance;

	private UserLogic(){
	}

	public static UserLogic getInstance(){
		if(instance == null){
			instance = new UserLogic(); 
		}
		return instance;
	}

	public void addUser(AddUserDto dto) throws SQLException{
		Connection con = DbConnection.createConnection(SystemInformation.getConnectionInformation());

		BpaUserTableRecord record = new BpaUserTableRecord();
		record.setUsername(dto.getUsername());
		record.setPassword(dto.getPassword());
		record.setGroupId(dto.getGroupLevelId());
		BpaUserTableDao userDao = new BpaUserTableDao();
		userDao.insert(con, record);
		con.close();
	}
	
	public void updateUser(UpdateUserDto dto) throws SQLException{
		Connection con = DbConnection.createConnection(SystemInformation.getConnectionInformation());

		BpaUserTableRecord record = new BpaUserTableRecord();
		record.setUsername(dto.getUsername());
		record.setPassword(dto.getPassword());
		record.setGroupId(dto.getGroupLevelId());
		BpaUserTableDao userDao = new BpaUserTableDao();
		BpaUserSrcCond cond = new BpaUserSrcCond();
		cond.setUsername(dto.getOldUsername());
		userDao.update(con, record, cond);
		con.close();
	}
	
	public void deleteUser(String username) throws SQLException{
		Connection con = DbConnection.createConnection(SystemInformation.getConnectionInformation());

		BpaUserTableDao userDao = new BpaUserTableDao();
		BpaUserSrcCond cond = new BpaUserSrcCond();
		cond.setUsername(username);
		userDao.delete(con, cond);
		con.close();
	}

	public boolean checkUserExist(AddUserDto dto) throws SQLException{
		Connection con = DbConnection.createConnection(SystemInformation.getConnectionInformation());
		BpaUserTableDao userDao = new BpaUserTableDao();
		BpaUserSrcCond cond = new BpaUserSrcCond();
		cond.setUsername(dto.getUsername());
		List list = userDao.executeQuery(con, cond);
		if(list.isEmpty()){
			return false;
		}
		return true;
	}
}
