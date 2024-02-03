package com.luv2code.aopdemo;

import com.luv2code.aopdemo.dao.AccountDAO;
import com.luv2code.aopdemo.dao.MembershipDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO){
		return runner ->{
			// demoTheBeforeAdvice(theAccountDAO, theMembershipDAO);
			//demoTheAfterReturningAdvice(theAccountDAO);
			demoTheAfterThrowingAdvice(theAccountDAO);

		};
	}

	private void demoTheAfterThrowingAdvice(AccountDAO theAccountDAO) {
		// call the method to find the accounts
		List<Account> theAccounts = null;
		try{
			// adding boolean flag to simulate exceptions
			boolean tripWire = true;
			theAccounts = theAccountDAO.findAccounts(tripWire);
		}catch (Exception exc){
			System.out.println("\n\nMain Program: .... caught exception: "+exc);
		}

		// display the accounts
		System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
		System.out.println("------");

		System.out.println(theAccounts);

		System.out.println("\n");
	}

	private void demoTheAfterReturningAdvice(AccountDAO theAccountDAO) {

		// call the method to find the accounts
		List<Account> theAccounts = theAccountDAO.findAccount();

		// display the accounts
		System.out.println("\n\nMain Program: demoTheAfterReturningAdvice");
		System.out.println("------");

		System.out.println(theAccounts);

		System.out.println("\n");

	}

	private void demoTheBeforeAdvice(AccountDAO theAccountDAO, MembershipDAO theMebershipDAO) {
		// call the business method
		Account myAccount = new Account();
		myAccount.setName("Madhu");
		myAccount.setLevel("Platinum");

		theAccountDAO.addAccount(myAccount,true);
		theAccountDAO.doWork();

		// call the accountdao getter/setter methods
		theAccountDAO.setName("Sabar");
		theAccountDAO.setServiceCode("Silver");

		String name = theAccountDAO.getName();
		String code = theAccountDAO.getServiceCode();


		// call the membership business method
		theMebershipDAO.addSillyMember();
		theMebershipDAO.goToSleep();

	}

}
