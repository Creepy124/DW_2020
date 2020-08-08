package model;

public class MyFile {
	private String fileName;
	private String fileType;
	
	public MyFile(String fileName, String fileType) {
		this.fileName = fileName;
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "MyFile [fileName=" + fileName + ", fileType=" + fileType + "]";
	}


}
