
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class ConsultaServlet
 */
public class ConsultaServlet extends HttpServlet {
	// El m´etodo doGet() se ejecuta una vez por cada petici´on HTTP GET.
	private String userName;
	private String password;
	private String url;
	private Integer contador;
	private ServletContext context;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Establecemos el tipo MIME del mensaje de respuesta
		response.setContentType("text/html");
		// Creamos un objeto para poder escribir la respuesta
		PrintWriter out = response.getWriter();
		Connection conn = null;
		Statement stmt = null;
		try {
			// Paso 1: Cargar el driver JDBC.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Paso 2: Conectarse a la Base de Datos utilizando la clase Connection
			// URL de la base de datos(equipo, puerto, base de datos)

			conn = DriverManager.getConnection(url, userName, password);
			// Paso 3: Crear sentencias SQL, utilizando objetos de tipo Statement
			stmt = conn.createStatement();
			// Paso 4: Ejecutar las sentencias SQL a trav´es de los objetos Statement
			String sqlStr = "select * from libros where autor = " + "’" + request.getParameter("autor") + "’";
			// Generar una p´agina HTML como resultado de la consulta
			out.println("<html><head><title>Resultado de la consulta</title></head><body>");
			out.println("<h3>Gracias por tu consulta.</h3>");
			out.println("<p>Tu consulta es: " + sqlStr + "</p>");
			ResultSet rset = stmt.executeQuery(sqlStr);
			// Paso 5: Procesar el conjunto de registros resultante utilizando ResultSet
			int count = 0;
			while (rset.next()) {
				out.println("<p>" + rset.getString("autor") + ", " + rset.getString("titulo") + ", "
						+ rset.getDouble("precio") + "</p>");
				count++;
			}
			out.println("<p>==== " + count + " registros encontrados =====</p>");
			// Se recupera la variable contador del ServletContext
			contador = (Integer) context.getAttribute("contador");
			out.println("<p>==== " + contador.intValue() + " peticiones *.html===</p>");
			out.println("</body></html>");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			out.close(); // Cerramos el flujo de escritura
			try {
				// Cerramos el resto de recursos
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// Se leen los parametros de inicializaci´on de Servlet y
		// se convierten en atributos del contexto para compartirlos con
		// cualquier servlet y JSP de la aplicaci´on
		// Se guarda el ServletContext que representa el contexto
		// de la aplicaci´on para usarlo en el doGet
		context = config.getServletContext();
		url = context.getInitParameter("URLBaseDeDatos");
		userName = context.getInitParameter("usuario");
		password = context.getInitParameter("password");
	}
}