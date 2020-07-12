package service;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class WritingError {
	
	public static void writingError(String error) throws IOException{
		FileService fileService = new FileServiceImpl();
		String message = LocalDateTime.now().toString()+"\n"+error+"\n ---------------- \n";
		fileService.writeLinesToFile("DataWareHouse2020\\local\\error.txt", message);
	}
	
	public static void sendError(String error) throws IOException, AddressException, MessagingException {
		writingError(error);
		JavaEmail.prepareSending(error);
	}//
	
	public static void main(String[] args) throws IOException {
		WritingError t = new WritingError();
		t.writingError("rew");
	}

}
