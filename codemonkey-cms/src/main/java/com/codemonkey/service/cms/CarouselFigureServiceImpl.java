package com.codemonkey.service.cms;


import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.cms.CarouselFigure;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.service.LogicalServiceImpl;

@Service
public class CarouselFigureServiceImpl extends LogicalServiceImpl<CarouselFigure> implements CarouselFigureService {

	@Override
	public CarouselFigure createEntity() {
		CarouselFigure carouselFigure = new CarouselFigure();
		return carouselFigure;
	}
	
	@Override
	protected Set<FieldValidation> validate(CarouselFigure entity) {
		Set<FieldValidation> set = super.validate(entity);
		
		if (entity.getPicture() == null) {
			set.add(new FormFieldValidation("picture", "图片文件不能为空！"));
		}
		 
		return set;
	}
	
	
	
}
