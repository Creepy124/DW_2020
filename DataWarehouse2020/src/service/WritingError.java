package service;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class WritingError {
	JavaEmail javaEmail = new JavaEmail();
	
	public void writingError(String error) throws IOException{
		FileService fileService = new FileServiceImpl();
		String message = LocalDateTime.now().toString()+"\n"+error+"\n ---------------- \n";
		fileService.writeLinesToFile("local\\error.txt", message);
	}
	
	public void sendError(String error) throws IOException, AddressException, MessagingException {
		writingError(error);
		javaEmail.prepareSending(error);
	}//
	
	public static void main(String[] args) throws IOException {
		WritingError t = new WritingError();
		t.writingError("rew");
	}

}
