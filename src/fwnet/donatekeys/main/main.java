package fwnet.donatekeys.main;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class main extends JavaPlugin{
	
	static main instance;
	
    public main() {
        instance = this;
     }
     
     public static main getPlugin() {
         return instance;
     }
	
     String hostname = getConfig().getString("mysql.hostname");
     String user = getConfig().getString("mysql.username");
     String password = getConfig().getString("mysql.password");
     String database = getConfig().getString("mysql.database");
     String port = getConfig().getString("mysql.port");
     String options = getConfig().getString("mysql.options");
     String table = getConfig().getString("mysql.table");
     String errormsg = getConfig().getString("plugin.errormsg");
     String successmsg = getConfig().getString("plugin.successmsg");
     String critical = getConfig().getString("plugin.sqlhackmsg");
     String noargmsg= getConfig().getString("plugin.noargmsg");
     
     String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + options;
     
     private static Connection con;
     private static Statement stmt;
     private static Statement stmt2;
     private static ResultSet rs;
	
	Logger log = getLogger();
	
	@Override
	public void onEnable() {
		log.info("Database url: " + url);
		log.info("DonateKeys started!");
		this.saveDefaultConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if(cmd.getName().equalsIgnoreCase("activate")) {
			
            if (args.length != 0) {
            	
            	String key = args[0];
    			String query = "select `key`, `valid`, `role` from `" + table + "` where `key`='" + key + "'";
                String query_upd = "UPDATE `" + table + "` SET `valid` = '0' WHERE `key`='" + key + "'";
                
            	if (args[0].contains("`") || args[0].contains("'")) {
            		sender.sendMessage(critical);
            	} else {
        			try {
        				
        	            con = DriverManager.getConnection(url, user, password);

        	            stmt = con.createStatement();
        	            stmt2 = con.createStatement();

        	            rs = stmt.executeQuery(query);
        	            
        	            while (rs.next()) {
        	            	
        	                int valid = rs.getInt(2);
        	                String role = rs.getString(3);
        	                
        	                if (valid == 1) {
        	                	getServer().dispatchCommand(getServer().getConsoleSender(), "pex user " + sender.getName() + " group set " + role);
        	                	sender.sendMessage(successmsg);
        	                	stmt2.executeUpdate(query_upd);
        	                }
        	                else {
        	                	sender.sendMessage(errormsg);
        	                }
        	            }
        			} catch (SQLException sqlEx) {
        	            sqlEx.printStackTrace();
        	        } finally {
        	            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        	            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        	            try { stmt2.close(); } catch(SQLException se) { /*can't do anything */ }
        	            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        	        }
            	}
            }
            if (args.length == 0) {
            	sender.sendMessage(noargmsg);
            	return false;
            }
		return true;
		}
		return false;
	}
}
