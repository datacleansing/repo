package io.datacleansing.service.dao;

import org.springframework.stereotype.Component;

import io.datacleansing.common.dao.AbstractDAO;
import io.datacleansing.service.representations.Service;

@Component
public class ServiceDAO extends AbstractDAO<Service>{

	public Service get(Service model) {
		return get(model.getRepository(), model.getId());
	}

	@Override
	protected Class<Service> getDomainClass() {
		return Service.class;
	}

}
