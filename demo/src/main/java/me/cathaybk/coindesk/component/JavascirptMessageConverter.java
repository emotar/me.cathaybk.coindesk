package me.cathaybk.coindesk.component;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JavascirptMessageConverter extends AbstractHttpMessageConverter {

	private ObjectMapper objectMapper = new ObjectMapper();

	public JavascirptMessageConverter() {
		super(new MediaType("application", "javascript", Charset.forName("UTF-8")));
	}

	@Override
	protected boolean supports(Class clazz) {
		return true;
	}

	@Override
	protected Object readInternal(Class clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		String body = IOUtils.toString(inputMessage.getBody(), Charset.forName("utf-8"));
		return this.objectMapper.readValue(body, clazz);
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// TODO Auto-generated method stub

	}

}
