package org.lacassandra.smooshyfaces.temporal;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JaxbDateAdapter extends XmlAdapter<String, Date> {
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	@Override
	public String marshal(Date date) throws Exception {
		return format.format(date);
	}

	@Override
	public Date unmarshal(String date) throws Exception {
		return format.parse(date);
	}

}
