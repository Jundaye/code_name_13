package ab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddrBookDAO {
	static String url = "jdbc:mysql://localhost:3306/newhr";
	static String userName = "root";
	static String password = "rootroot";

	public static List<AddrBookTO> getList() {
		List<AddrBookTO> result = new ArrayList<AddrBookTO>();
		try (Connection conn = DBUtil.getConnection()) {
			String sql = "SELECT * from addrbook";
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				AddrBookTO ab = new AddrBookTO();
				ab.setAbId(rs.getInt("ab_id"));
				ab.setAbName(rs.getString("ab_name"));
				ab.setAbComdept(rs.getString("ab_comdept"));
				ab.setAbEmail(rs.getString("ab_email"));
				ab.setAbBirth(rs.getString("ab_birth"));
				ab.setAbTel(rs.getString("ab_tel"));
				ab.setAbMemo(rs.getString("ab_memo"));
				result.add(ab);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void insert(AddrBookTO ab) {
		 try (Connection conn = DBUtil.getConnection()) {

		        String sql = "INSERT INTO addrbook "
		                   + "(ab_name, ab_email, ab_comdept, ab_birth, ab_tel, ab_memo) "
		                   + "VALUES (?, ?, ?, ?, ?, ?)";

		        PreparedStatement psmt = conn.prepareStatement(sql);

		        psmt.setString(1, ab.getAbName());
		        psmt.setString(2, ab.getAbEmail());
		        psmt.setString(3, ab.getAbComdept());
		        psmt.setString(4, ab.getAbBirth());
		        psmt.setString(5, ab.getAbTel());
		        psmt.setString(6, ab.getAbMemo());

		        psmt.executeUpdate();

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
	}
	
}