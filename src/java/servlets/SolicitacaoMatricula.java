
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SolicitacaoMatricula", urlPatterns = {"/SolicitacaoMatricula"})
public class SolicitacaoMatricula extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/lista1";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        Connection conn;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String matricula = request.getParameter("matricula");
            String aluno = request.getParameter("aluno");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Solicitação de Matrícula</title>"); 
            out.println("<style type=\"text/css\">" +
                "<!-- " +
                "body {background-color:beige; color:black; font-size:90%}"+
                "td   {font-size: 90%; background-color:white; color: black}" +
                "//--></style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2> Disciplinas Cadastradas com sucesso </h2>");
            out.println("<table border=2<tr>");
            out.println("<td><b>Matrícula</b></td> <td><b>Nome do Aluno</b></td></tr>") ;
            out.println("<tr><td>"+ matricula + "</td>" +  "<td>" + aluno + "</td></tr> <br />");
            
            
            try {
	            Class.forName( JDBC_DRIVER );
	            conn = DriverManager.getConnection( DATABASE_URL, 
                            "root", "" );
                 
            
            
                    if (request.getParameter("btnSolicitar") != null && request.getParameter("btnSolicitar").equals("Enviar")){
                
                    //codigo dizendo o que deve ser feito qnd o botao for clicado               
                    String[] codSolicitadas = request.getParameterValues("codigo");
                
                String monsterDisciplina = ""; //trata o parametro pra usar no select
                for (int i = 0; i < codSolicitadas.length; i++){
                    if (i == 0)
                    {
                        monsterDisciplina += "codigo_disciplina='" + codSolicitadas[i] +"'";
                    } 
                    else
                    {
                        monsterDisciplina += " or codigo_disciplina='" + codSolicitadas[i] + "'";
                    }
}
  
             try (Statement st = conn.createStatement()) {
                     ResultSet rec= st.executeQuery( 
                             "Select * " +
                                      " From disciplinas natural join turmas_ofertadas " +
                                     "Where " + monsterDisciplina );
                      
            out.println("<br />");
            out.println("<table border=1><tr>");
            out.println("<td><b>Código da disciplina</b></td><td><b>Nome da Disciplina </b></td> <td><b>Código da turma</b></td><td><b>Horário</b></td><td><b>Carga Horária Total</b></td></tr>") ;         
            while(rec.next()){
            
            
            out.println("<tr><td>"+ rec.getString(1) + "</td><td>"+ rec.getString(2) + "</td><td>" + rec.getString(4) + "</td><td>" + rec.getString(5) + "</td><td>" + rec.getString(3) + "</td></tr>"); 
            
        }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
    }
      catch (SQLException s) {
	            out.println("SQL Error: " + s.toString() + " "
	                + s.getErrorCode() + " " + s.getSQLState());
	        
      } 
              
                
             }
        }
            catch (ClassNotFoundException e) {
	            out.println("Error: " + e.toString()
	                + e.getMessage());   
        }
            
        }}       
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SolicitacaoMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SolicitacaoMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SolicitacaoMatricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SolicitacaoMatricula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
        
    
                   

 