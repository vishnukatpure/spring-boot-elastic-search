package com.example.repository.es;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("deprecation")
@Service
public class EmployeeESRepo {

	private final String INDEX = "employee-data";
	private final String TYPE = "employees";

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private ObjectMapper objectMapper;

	public boolean checkIfIndexExists() throws IOException {
		Response response = restHighLevelClient.getLowLevelClient().performRequest("HEAD", "/" + INDEX);
		int statusCode = response.getStatusLine().getStatusCode();
		return (statusCode != 404);
	}

	public Employee insert(Employee employee) {
		Map<?, ?> dataMap = objectMapper.convertValue(employee, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, employee.getId().toString()).source(dataMap)
				.create(true);
		try {
			restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		} catch (Exception ex) {
			ex.getLocalizedMessage();
		}
		return employee;
	}

	public Employee update(Employee employee) {
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, employee.getId().toString()).fetchSource(true);
		try {
			String employeeJson = objectMapper.writeValueAsString(employee);
			updateRequest.doc(employeeJson, XContentType.JSON);
			restHighLevelClient.update(updateRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	public void delete(Employee employee) {
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, employee.getId().toString());
		try {
			restHighLevelClient.delete(deleteRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Employee search(Long id) {
		GetRequest getRequest = new GetRequest(INDEX, TYPE, id + "");
		String resp = "";
		try {
			checkIfIndexExists();
			resp = restHighLevelClient.get(getRequest).getSourceAsString();
			return objectMapper.readValue(resp, Employee.class);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
