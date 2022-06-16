package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties  myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency,
								  MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) {
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional(){
		User test1 = new User("TestTrans1", "testtrans@mail.com",LocalDate.now());
		User test2 = new User("TestTrans2", "testtrans2@mail.com",LocalDate.now());
		User test3 = new User("TestTrans3", "testtrans@mail.com",LocalDate.now());
		User test4 = new User("TestTrans4", "testtrans4@mail.com",LocalDate.now());

		List<User> users = Arrays.asList(test1,test2, test3, test4);
		try{
		userService.saveTransactional(users);
		}catch (Exception e) {
			LOGGER.error("Error tipo Exception metodo transaccional"+e);
		}
			userService.getAllUsers().stream()
					.forEach(user -> LOGGER.info("Este es el usuario de metodo transaccional" + user));

	}

	private void getInformationJpqlFromUser(){
		/*LOGGER.info("User with method findByUserEmail" +
				userRepository.findByUserEmail("cesar@mail.com")
						.orElseThrow(()->new RuntimeException("No se encontro usuario")));

		userRepository.findAndSort("user", Sort.by("id").ascending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario con metodo sort "+ user));

		userRepository.findByName("Cesar")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con query method " + user));

		LOGGER.info("Usuario queryMethod findByeEmailAndName" +
				userRepository.findByEmailAndName("user4@mail.com", "user4")
						.orElseThrow(()-> new RuntimeException("No encontro usuario methodName")));

		userRepository.findByNameLike("%e%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con findByNameLike " + user));

		userRepository.findByNameOrEmail("user9", null)
				.stream()
				.forEach(user -> LOGGER.info("Usuario con findByNameOrEmail " + user));


		userRepository.findByBirthDateBetween(LocalDate.of(2021, 3, 1),
						LocalDate.of(2022, 3, 1))
				.stream()
				.forEach(user -> LOGGER.info("Usuario con findByBirthDateBetween " + user));


		userRepository.findByNameContainingOrderByIdDesc("user")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con findByNameLikeOrderByIdDesc " + user));
		 */

		LOGGER.info("El usuarion con getAllByBirthDateAndEmail " +
				userRepository.getAllByBirthDateAndEmail(LocalDate.of(2010,10, 20), "cesar@mail.com")
				.orElseThrow(()-> new RuntimeException("No encontro usuario getAllByBirthDateAndEmail")));
	}

	private void saveUsersInDataBase(){
		User user1 = new User("Cesar", "cesar@mail.com", LocalDate.of(2010,10, 20));
		User user2 = new User("Cesar", "user2@mail.com", LocalDate.of(2021,1, 21));
		User user3 = new User("user3", "user3@mail.com", LocalDate.of(2015,2, 22));
		User user4 = new User("user4", "user4@mail.com", LocalDate.of(2014,3, 23));
		User user5 = new User("user5", "user5@mail.com", LocalDate.of(2021,4, 24));
		User user6 = new User("user6", "user6@mail.com", LocalDate.of(2015,5, 25));
		User user7 = new User("user7", "user7@mail.com", LocalDate.of(2021,6, 26));
		User user8 = new User("user8", "user8@mail.com", LocalDate.of(2021,7, 27));
		User user9 = new User("user9", "user9@mail.com", LocalDate.of(2008,8, 28));
		User user10 = new User("user10", "user10@mail.com", LocalDate.of(2003,9, 29));
		List<User> listUser = Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10);
		listUser.stream().forEach(userRepository::save);
	}

	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail()+"-"+userPojo.getPassword()+"-"+userPojo.getAge());
		try{
			int value = 10/0;
			LOGGER.debug("Mi value: "+value);
		}catch (Exception e){
			LOGGER.error("Error al dividir por 0 " +e.getStackTrace());
		}
	}
}
