package com.redfire.nutrieduc.commonsvc.rest;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.redfire.nutrieduc.commonsvc.response.OperationServiceResponse;


public class LoadRestOperation {

	public List<OperationServiceResponse> loadOperations (Collection<Class<?>> classes) throws IOException, ClassNotFoundException {
		List<OperationServiceResponse> response = new ArrayList<>();
		for (Class clazz : classes) {

			RequestMapping rootMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
			for (Method method : clazz.getMethods()) {
				RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
				if (methodMapping != null) {
					String methodPath = methodMapping.value() != null &&  methodMapping.value().length > 0 ? methodMapping.value()[0] : "";
					OperationServiceResponse item = new OperationServiceResponse();

					item.setClassController(clazz.getName());
					item.setOperation(rootMapping.value()[0] + methodPath);
					item.setMethod(methodMapping.method()[0].toString());
					Annotation[][] annotations = method.getParameterAnnotations();
					int indexOfRequestBody = -1;
					outLoop : for (Annotation[] clazzParam : annotations ) {
						indexOfRequestBody++;
						for (Annotation annotation : clazzParam) {
							if (annotation.annotationType().equals(RequestBody.class))
								break outLoop;
						}
					}
					if (indexOfRequestBody >= 0)
						item.setClassRequest(method.getParameterTypes()[indexOfRequestBody].getName());
					
					item.setClassResponse(method.getReturnType().getName());
					
					response.add(item);
				}
			}
		}
		return response;
	}
}
