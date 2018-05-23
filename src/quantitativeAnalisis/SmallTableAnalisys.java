package quantitativeAnalisis;


import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.text.StyledEditorKit.BoldAction;


public class SmallTableAnalisys {

	public boolean createSmallTable(boolean drop) {

		boolean result = false;

		try {
			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();
			Statement s = conn.createStatement();

			if (drop)result = s.execute("DROP TABLE smalltable ");
			System.out.println("DROP TABLE smalltable ");

			result = s.execute("CREATE TABLE smalltable "
					+ "( agent character varying(255), "
					+ "behavior character varying(255), "
					+ "systemtime numeric, " + "simulationtime numeric, "
					+ "error double precision, " + "delay double precision, "
					+ "experiment numeric, " + "nragents numeric,"
					+ "loadlevel numeric," + " void character varying(255))");


			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("CREATE TABLE smalltable ");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean insertDataSmallTablefromBigtable(int numeroAgentesAmostra) {

		boolean result = false;
		java.sql.Connection conn = ConSingletonPgsql.getInstance().getConn();

		Statement s;

		for (int i = 1; i <= 4; i++) {

			for (int j = 100; j <= 3000; j = j + 100) {

				for (int k = 1; k <= numeroAgentesAmostra; k++) {

					for (int loadlevel = 1; loadlevel <= 19; loadlevel++)

						try {
							s = conn.createStatement();
							s.execute("INSERT INTO smalltable SELECT  agent, behavior ,  systemtime ,  simulationtime ,  error ,  delay ,"
									+ i
									+ ",   "
									+ j
									+ ", "
									+ loadlevel
									+ " FROM bigtable"
									+ " WHERE " 
									+ " loadlevel="
									+ loadlevel 

									+ " and experiment="
									+ i
									+ " and nragents="
									+ j + 
											" and agent='BU45.1.3_"
									+ k
									 + "' LIMIT 20");
							/*
							 * System.out
							 * .println("executando insercao para agent='BU45.1.3_"
							 * + k + " do " + i + "o experimento com " + j +
							 * " agentes no nivel de carga " + loadlevel);
							 */
							s.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						/*
						 * System.out
						 * .println("executando insercao para agent='BU45.1.3_" + k
						 * + " do " + i + "o experimento com " + j + " agentes");
						 */

				}
				System.out.println("executando insercao  do " + i
						+ "o experimento com " + j + " agentes");
			}
		}
		return result;
	}

	public boolean insertDataSmallTable(int numeroAgentesAmostra) {

		boolean result = false;

		for (int i = 1; i <= 4; i++) {

			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();

			Statement s;

			for (int j = 100; j <= 3000; j = j + 100) {

				for (int k = 1; k <= numeroAgentesAmostra; k++) {
					for (int loadlevel = 1; loadlevel <= 19; loadlevel++)
						try {
							s = conn.createStatement();
							s.execute("INSERT INTO smalltable SELECT  agent, behavior ,  systemtime ,  simulationtime ,  error ,  delay ,"
									+ i
									+ ",   "
									+ j
									+ ", "+loadlevel+" FROM behaviourregisterexp"
									+ i
									+ "a"
									+ j
									+ " WHERE agent='BU45.1.3_"
									+ k
									+ "' and simulationtime > 75000*"
									+ loadlevel + " and simulationtime <( (75000*"
									+ loadlevel + 1 +") +1) LIMIT 20");
							/*
							 * System.out
							 * .println("executando insercao para agent='BU45.1.3_"
							 * + k + " do " + i + "o experimento com " + j +
							 * " agentes no nivel de carga " + loadlevel);
							 */
							s.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						/*
						 * System.out
						 * .println("executando insercao para agent='BU45.1.3_" + k
						 * + " do " + i + "o experimento com " + j + " agentes");
						 */

				}
				System.out.println("executando insercao  do " + i
						+ "o experimento com " + j + " agentes");
			}
			
		}
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean removemloadlevel0() {

		System.out
		.println("removendo amostras anteriores a 75000 milisegundos de simulacao");
		boolean result = false;

		try {
			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();
			Statement s = conn.createStatement();

			result = s.execute("delete from smalltable where loadlevel=0");

			System.out
			.println("removidos amostras anteriores a 75000 milisegundos de simulacao");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {

		SmallTableAnalisys smallTableAnalisys = new SmallTableAnalisys();

		boolean drop = true;
		smallTableAnalisys.createSmallTable(drop);


		 smallTableAnalisys.insertDataSmallTable(10); // nr de agentes da
		// mostra
		//smallTableAnalisys.insertDataSmallTablefromBigtable(1);


		smallTableAnalisys.removemloadlevel0();

	}
}
