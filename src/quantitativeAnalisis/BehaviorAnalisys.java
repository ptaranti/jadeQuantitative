package quantitativeAnalisis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTReader;

public class BehaviorAnalisys {

	public static boolean createTablesBehaviourRegister() {

		boolean result = false;

		try {
			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();
			Statement s = conn.createStatement();

			for (int i = 1; i <= 4; i++) {

				result = s
				.execute("CREATE TABLE behaviourregisterexp"
						+ i
						+ "a10 ( agent character varying(255), behavior character varying(255), systemtime numeric, simulationtime numeric, error double precision, delay double precision, void character varying(255))");

				for (int j = 100; j <= 3000; j = j + 100) {
					result = s
					.execute("CREATE TABLE behaviourregisterexp"
							+ i
							+ "a"
							+ j
							+ " ( agent character varying(255), behavior character varying(255), systemtime numeric, simulationtime numeric, error double precision, delay double precision, void character varying(255))");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean createAnalisysResultTable() {

		boolean result = false;

		try {
			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();
			Statement s = conn.createStatement();

			result = s
			.execute("CREATE TABLE analisysBehavior ( exp character varying(255), numberagents numeric,totalamostras numeric, avgerror double precision, stderror double precision, maxerror double precision, avgdelay double precision, stddelay double precision, maxdelay double precision, void character varying(255) )");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean analisysResult() {

		boolean result = false;

		try {
			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();
			Statement s = conn.createStatement();


			for (int i = 1; i <= 4; i++) {
				String experimento = "Exp" + i;
				ResultSet r = s
				.executeQuery("SELECT count(error) as total_Amostras, avg(error) AS average_error, stddev(error) AS stddev_error, max(error) AS max_error, avg(delay) AS average_delay, stddev(delay) AS stddev_delay, (min(delay)* (-1)) AS max_delay FROM behaviourregisterexp"
						+ i + "a10 where (simulationtime > 0)");

				//while (r.next()) {
				r.next();

				s.execute(" INSERT into analisysBehavior VALUES ( '"
						+ experimento + "', " + new Integer(10) + ", "
						+ r.getInt(1) + ", " + r.getDouble(2) + ", "
						+ r.getDouble(3) + ", " + r.getDouble(4) + ", "
						+ r.getDouble(5) + ", " + r.getDouble(6) + ", "
						+ r.getDouble(7) + ")");

				System.out.println("analizado " + experimento + " com 10 agentes");
				//}
				for (int j = 100; j <= 3000; j = j + 100) {

					r = s
					.executeQuery("SELECT count(error) as total_Amostras, avg(error) AS average_error, stddev(error) AS stddev_error, max(error) AS max_error, avg(delay) AS average_delay, stddev(delay) AS stddev_delay, (min(delay)* (-1)) AS max_delay FROM behaviourregisterexp"
							+ i
							+ "a"
							+ j
							+ " where (simulationtime > + 0)");

					r.next();

					s.execute(" INSERT into analisysBehavior VALUES ( '"
							+ experimento + "', " + j + ", "
							+ r.getInt(1) + ", " + r.getDouble(2) + ", "
							+ r.getDouble(3) + ", " + r.getDouble(4) + ", "
							+ r.getDouble(5) + ", " + r.getDouble(6) + ", "
							+ r.getDouble(7) + ")");

					System.out.println("analizado " + experimento + " com "+j+" agentes");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean insertDataBehaviourRegister() {

		boolean result = false;

		try {
			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();
			Statement s = conn.createStatement();

			for (int i = 1; i <= 4; i++) {

				result = s
				.execute("COPY behaviourregisterexp"
						+ i
						+ "a10 FROM '/trabalho/Trabalho/PUC-Rio/Doutorado/Disciplinas/2010.2/Qualificacao/Exp/Exp"
						+ i
						+ "/Agentes-10/BehaviourRegister.csv' CSV QUOTE '\"' delimiter ';'");

				System.out.println("inserida tabela behaviourregisterexp" + i
						+ "a10");

				System.out
				.println("inserida dados na tabela behaviourregisterexp"
						+ i + "a10");

				for (int j = 100; j <= 3000; j = j + 100) {
					result = s
					.execute("COPY behaviourregisterexp"
							+ i
							+ "a"
							+ j
							+ " FROM '/trabalho/Trabalho/PUC-Rio/Doutorado/Disciplinas/2010.2/Qualificacao/Exp/Exp"
							+ i
							+ "/Agentes-"
							+ j
							+ "/BehaviourRegister.csv' CSV QUOTE '\"' delimiter ';'");

					System.out.println("inserida tabela behaviourregisterexp"
							+ i + "a" + j);

					System.out
					.println("inserida dados na tabela behaviourregisterexp"
							+ i + "a" + j);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}


	public static boolean removemSimulationTime(int removeMilliseconds) {
		removeMilliseconds++;

		System.out.println("removendo amostras anteriores a "+ removeMilliseconds +" milisegundos de simulacao");
		boolean result = false;

		try {
			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();
			Statement s = conn.createStatement();

			for (int i = 1; i <= 4; i++) {

				result = s
				.execute("delete from behaviourregisterexp" + i +  "a10" + " where simulationtime<"+ removeMilliseconds );


				System.out
				.println("removendo dados na tabela behaviourregisterexp"
						+ i + "a10 com tempo de simulacao em millisec anterior a " + removeMilliseconds);

				for (int j = 100; j <= 3000; j = j + 100) {
					result = s
					.execute("delete from behaviourregisterexp" + i + "a" + j + "  where simulationtime<"+ removeMilliseconds );

					System.out
					.println("removendo dados na tabela behaviourregisterexp"
							+ i + "a" + j +" com tempo de simulacao em millisec anterior a " + removeMilliseconds);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}


	public static void main(String[] args) {
				createTablesBehaviourRegister();
		insertDataBehaviourRegister();
		createAnalisysResultTable();
		analisysResult();
		removemSimulationTime(60000); //milliseconds
	}

}
