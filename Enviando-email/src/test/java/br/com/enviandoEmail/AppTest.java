package br.com.enviandoEmail;

public class AppTest {	
	
	@org.junit.Test
	public void testeEmail() throws Exception{
		
		StringBuilder stringBuilderTextoEmail = new StringBuilder();
			
			stringBuilderTextoEmail.append("Olá, <br/><br/>"); 
			stringBuilderTextoEmail.append("<h2>Você Estar Recebendo acesso a platafaforma de Dentista</h2> <br/><br/>");
			stringBuilderTextoEmail.append("Para Ter Acesso clique no botão aqui a baixo <br/><br/>");
			
			stringBuilderTextoEmail.append("<b>Login: </b> ConradoCamelo<br/>"); 
			stringBuilderTextoEmail.append("<b>Senha: </b> Mandriva<br/><br/>");
			
			stringBuilderTextoEmail.append("<a target =\"_blank\" href=\"http://projetojavaweb.com/certificado-aluno/login\" style=\"color:#2525a7; padding: 14px 25px; text-align:center; text-decoration: none; display:inline-block; border-radius:30px; font-size:20px; font-family:courier; border: 3px solid blue;background-color:#0099FF;\" >Acessar Portal do Aluno</a>");
			
		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("conradocamelo@gmail.com", 
				"Conrado Camelo", "Teste", stringBuilderTextoEmail.toString());
		
		enviaEmail.enviarEmailAnexo(true);
		
		
		Thread.sleep(5000);
		
	}
	
	
}
