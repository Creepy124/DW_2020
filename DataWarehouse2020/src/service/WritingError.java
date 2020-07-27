package service;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class WritingError {
	

	public static void writingError(String error) throws IOException {
		FileService fileService = new FileServiceImpl();
		String message = LocalDateTime.now().toString() + "\n" + error + "\n ---------------- \n";
		fileService.writeLinesToFile("E:\\Warehouse\\DW_2020\\DataWareHouse2020\\local\\error.txt", message);
	}

	public static void sendError(String error, String toEmails) {
		try {
			writingError(error);
			JavaEmail.prepareSending(error, toEmails);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		WritingError t = new WritingError();
		t.writingError("rew");
	}


}
