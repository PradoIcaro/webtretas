package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luke
 */
@WebServlet(urlPatterns = {"/ServletOfertadas"})
public class ServletOfertadas extends HttpServlet {
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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        Connection conn;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Turmas Ofertadas</title>"); 
            out.println("<style type=\"text/css\">" +
                "<!-- " +
                "body {background-color:beige; color:black; font-size:90%}"+
                "td   {font-size: 90%; background-color:white; color: black}" +
                "//--></style>");
            out.println("</head>");
            out.println("<body>");
            String mat_aluno="";
            String nome_aluno="";
            
            try {
	            Class.forName( JDBC_DRIVER );
	            conn = DriverManager.getConnection( DATABASE_URL, 
                            "root", "" );
                    String matricula = request.getParameter("matricula");
                    
                    
                try (Statement st = conn.createStatement()) {
                     ResultSet rec= st.executeQuery(
                            
                            "SELECT * " +
                                    "FROM alunos " +
                                    "WHERE " +
                                    ("matricula='" + matricula + "'"));
                                    rec.next();
                                   matricula= rec.getString("matricula");
                                    if (matricula.equals(""))
                                    {
                                        
                                        out.println("matricula inválida");
                                    }
                                    else{
                        //a merda ta sendo aqui em cima
                                out.println("<table border=2><tr>");
                                out.println("<td><b>Matrícula</b></td> <td><b>Nome do Aluno</b></td></tr>") ;
                                mat_aluno=rec.getString(2);
                                nome_aluno=rec.getString(1);
                                out.println("<tr><td>"+ mat_aluno + "</td>" +  "<td>" + nome_aluno + "</td></tr>"); 
                    
                                out.println("</table>");
                       
                                    }
                        out.println("<h2>Disciplinas disponíveis abaixo </h2>");
                        ResultSet rec2= st.executeQuery(
                            "SELECT * " +
                            "FROM turmas_ofertadas " );
                        out.println("<br />");
                
                out.println("<form action=SolicitacaoMatricula method=get name=solicitadas>");
                
                out.println("<table border=1><tr>");
                out.println("<td><b>Código da disciplina</b></td> <td><b>Código da turma</b></td><td><b>Horário</b></td><td><b>Incluir</b></td></tr>") ;
                while(rec2.next())
                    {
                         out.println("<tr><td>" + rec2.getString(1) + "</td>" +  "<td>" + rec2.getString(2) + "</td>"+  "<td>" + rec2.getString(3) +"</td>"+ "<td> <input type=checkbox name=codigo value=" +rec2.getString(1) +  " </td></tr>");
                                               
                    }
                                                
                out.println("</table>");
                
                
                out.println("<input type='hidden' name='matricula' value='" + mat_aluno + "'> ");
                out.println("<input type='hidden' name='aluno'value='" + nome_aluno + "' >");
                out.println("<input type='submit' value='Enviar' name='btnSolicitar' >");
                out.println("</form>");
                }
                    
                   
            }
            catch (SQLException s) {
	            out.println("SQL Error: " + s.toString() + " "
	                + s.getErrorCode() + " " + s.getSQLState());
	        } catch (ClassNotFoundException e) {
	            out.println("Error: " + e.toString()
	                + e.getMessage());
	        }
                        
            
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
