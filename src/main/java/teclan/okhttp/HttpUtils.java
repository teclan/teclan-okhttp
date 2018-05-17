package teclan.okhttp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);


	public static String uploadFile(String url, List<String> filePaths) {
		return uploadFile(url, filePaths, null, null);
	}

	public static String uploadFile(String url, List<String> filePaths, String jsonName, JSONObject json) {

		OkHttpClient client = new OkHttpClient();

		List<FileInfo> fileInfos = new ArrayList<FileInfo>();

		for (String filePath : filePaths) {
			File file = new File(filePath);

			if (!file.exists() || file.isDirectory()) {
				LOGGER.warn(" {} 不是有效的文件 ...");
				continue;
			}

			RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
			
			FileInfo fileInfo = new FileInfo("file", file.getName(), fileBody);

			fileInfos.add(fileInfo);
		}

		Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

		for (FileInfo fileInfo : fileInfos) {
			builder.addFormDataPart(fileInfo.getName(), fileInfo.getFileName(), fileInfo.getFileBody());
		}

		RequestBody requestBody = (jsonName != null && json != null)
				? builder.addFormDataPart(jsonName, json.toJSONString()).build()
				: builder.build();


		Request request = new Request.Builder().url(url).post(requestBody).build();
		try {
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	public static void main(String[] args) {

		List<String> filePaths = new ArrayList<String>();
		filePaths.add("E:\\redis.txt");
		filePaths.add("E:\\1.jpg");

		JSONObject json = new JSONObject();
		json.put("ownerId", 1);
		json.put("mapName", "防区图1");
		json.put("mapId", "1");
		json.put("ownerId", 1);
		json.put("ownerId", 1);

		String result = uploadFile("http://192.168.3.134:8081/IntegratedMM/file/upload.do", filePaths, "jsonStr", json);

		System.out.println(result);
	}


}
