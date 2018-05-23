package quantitativeAnalisis;

import java.sql.SQLException;
import java.sql.Statement;


public class BigTableAnalisys {

	public boolean createBigTable() {

		boolean result = false;

		try {
			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();
			Statement s = conn.createStatement();

			System.out.print("O sistema espera uma bigtable - - se nao existir -> crie!");
			
				System.out.println("apagando bigtable");
				result = s.execute("DROP TABLE bigtable ");
				
				System.out.println("aguarde criacao");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				System.out.println("\nbigtable apagada!");
				System.out.println("\ncriando nova bigtable vazia");
				
				
				
				result = s
				.execute("CREATE TABLE bigtable "
						+ "( agent character varying(255), "
						+ "behavior character varying(255), "
						+ "systemtime numeric, "
						+ "simulationtime numeric, "
						+ "error double precision, "
						+ "delay double precision, "
						+ "experiment numeric, " + "nragents numeric,"
						+ "loadlevel numeric,"
						+ " void character varying(255))");
				
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("bigtable criada");
		
		return result;
	}

	public boolean insertDataBigTable() {

		System.out.println("iniciando inserção");
		
		boolean result = false;

		for (int i = 1; i <= 4; i++) {

			java.sql.Connection conn = ConSingletonPgsql.getInstance()
			.getConn();

			Statement s;

			try {
				System.out.println("iniciando insercao do " + i
						+ "o experimento com 10 agentes");
				
				s = conn.createStatement();
				s.execute("INSERT INTO bigtable SELECT  agent, behavior ,  systemtime ,  simulationtime ,  error ,  delay ,"
						+ i
						+ ",   10, div(simulationtime ,75000 ) FROM behaviourregisterexp"
						+ i
						+ "a10 WHERE simulationtime > 0");
				
				System.out.println("encerrada insercao do " + i
						+ "o experimento com 10 agentes \n");
				s.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int j = 100; j <= 3000; j = j + 100) {

				try {
					System.out.println("iniciando insercao do " + i
							+ "o experimento com " + j + " agentes");
					
					s = conn.createStatement();
					s.execute("INSERT INTO bigtable SELECT  agent, behavior ,  systemtime ,  simulationtime ,  error ,  delay ,"
							+ i
							+ ",   "
							+ j
							+ ", div(simulationtime ,75000) FROM behaviourregisterexp"
							+ i
							+ "a" + j + " WHERE simulationtime > 0");
					
					System.out.println("encerrada insercao do " + i
							+ "o experimento com " + j + " agentes\n");
					s.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		return result;
	}

	public static void main(String[] args) {

		BigTableAnalisys bigTableAnalisys = new BigTableAnalisys();

		bigTableAnalisys.createBigTable();

	
		
		

		bigTableAnalisys.insertDataBigTable();

	}
}
