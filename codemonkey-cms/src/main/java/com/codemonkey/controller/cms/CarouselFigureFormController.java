package com.codemonkey.controller.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.cms.CarouselFigure;
import com.codemonkey.service.cms.CarouselFigureService;
import com.codemonkey.web.controller.AbsFormExtController;

@Controller
@RequestMapping("/ext/carouselFigure/**")
public class CarouselFigureFormController extends AbsFormExtController<CarouselFigure>{

	@Autowired private CarouselFigureService carouselFigureService;

	@Override
	protected CarouselFigureService service() {
		return carouselFigureService;
	}
}
