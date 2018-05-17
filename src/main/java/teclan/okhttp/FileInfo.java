package teclan.okhttp;

import okhttp3.RequestBody;

public class FileInfo {

	private String name;
	private String fileName;
	private RequestBody fileBody;

	public FileInfo() {
	}

	public FileInfo(String name, String fileName, RequestBody fileBody) {
		this.name = name;
		this.fileName = fileName;
		this.fileBody = fileBody;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public RequestBody getFileBody() {
		return fileBody;
	}

	public void setFileBody(RequestBody fileBody) {
		this.fileBody = fileBody;
	}
}
