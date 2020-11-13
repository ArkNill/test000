package kr.com.inspect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.script.ScriptTemplateViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.com.inspect"})
public class ServletConfig implements WebMvcConfigurer {
	@Value("${report.docx.directory}")
	private String docxPath;
	
	@Value("${report.xlsx.directory}")
	private String xlsxPath;
	
	@Value("${report.hwp.directory}")
	private String hwpPath;
	
	@Value("${report.pptx.directory}")
	private String pptxPath;
	
	/* 정적 자원 관리 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/reports/hwp/**").addResourceLocations(hwpPath);
		registry.addResourceHandler("/reports/docx/**").addResourceLocations(docxPath);
		registry.addResourceHandler("/reports/xlsx/**").addResourceLocations(xlsxPath);
		registry.addResourceHandler("/reports/pptx/**").addResourceLocations(pptxPath);
		registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
	}
	  
	/* 뷰 영역 설정 */
	public void configureViewResolvers(ViewResolverRegistry registry) {
	    InternalResourceViewResolver bean = new InternalResourceViewResolver();
	    bean.setViewClass(JstlView.class);
	    bean.setPrefix("/WEB-INF/views/");
	    bean.setSuffix(".jsp");
	    registry.viewResolver((ViewResolver)bean);
	}
	  
	/* 파일업로드 용량, 인코딩 처리 설정 */
	@Bean(name = {"multipartResolver"})
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(1000000000L);
	    multipartResolver.setDefaultEncoding("UTF-8");
	    return multipartResolver;
	}

}
