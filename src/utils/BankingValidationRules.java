package utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

import com.banking.AcType;
import com.banking.BankAccount;

import custom_exceptions.DuplicateAccNo;
import custom_exceptions.InsufficientFundsException;
import custom_exceptions.InvalidAccountNumber;

import java.util.*;
public class BankingValidationRules {
	// add a method to validate min balance
	public static double validateBalance(AcType type, double balance) throws InsufficientFundsException {
		if (balance <= type.getMinBalance())
			throw new InsufficientFundsException("Balance too low!!!!");
		else
			return balance;
	}
	//duplicate Account no
	public static int checkDuplicateAccNo(int accNo,Map<Integer, BankAccount>bank) throws DuplicateAccNo{
		if(bank.containsKey(accNo)) {
			throw new DuplicateAccNo("Account number already exist");
		}
		else {
			return accNo;
		}
	}
	//deposit money
	public static void depositeAmount(int accountNo,double amount,Map<Integer,BankAccount>bank) throws InvalidAccountNumber{
		for(Map.Entry<Integer,BankAccount> m:bank.entrySet()) {
			if(m.getValue().getAcctNo()==accountNo) {
				double finalBalance=m.getValue().deposit(amount);
				m.getValue().setBalance(finalBalance);
			}
			else {
				throw new InvalidAccountNumber("Account not Found!!!");
			}
		}
	}
	//withdraw money
	public static void withdrawAmount(int accountNo,double amount,Map<Integer,BankAccount>bank) throws InvalidAccountNumber,InsufficientFundsException{
		for(Map.Entry<Integer,BankAccount> m:bank.entrySet()) {
			if(m.getValue().getAcctNo()==accountNo) {
				double finalBalance=m.getValue().withdraw(amount);
				m.getValue().setBalance(finalBalance);
			}
			else {
				throw new InvalidAccountNumber("Account not Found!!!");
			}
		}
	}
	//transfer amount
	public static void transferAmount(int AccTransferedFrom,int AccTransferedTo,double amount,Map<Integer,BankAccount>bank) throws InvalidAccountNumber,InsufficientFundsException{
		if(bank.containsKey(AccTransferedFrom) && bank.containsKey(AccTransferedTo)) {
			for(Map.Entry<Integer,BankAccount> m:bank.entrySet()) {
				if(m.getValue().getAcctNo()==AccTransferedFrom) {
					double finalBalance=m.getValue().withdraw(amount);
					m.getValue().setBalance(finalBalance);
				}
				if(m.getValue().getAcctNo()==AccTransferedTo) {
					double finalBalance=m.getValue().deposit(amount);
					m.getValue().setBalance(finalBalance);
				}
			}
		}
		else {
			throw new InvalidAccountNumber("Wrong Account number");
		}
	}
	// validate a/c type
	public static AcType parseAndValidateType(String type) {
		return AcType.valueOf(type.toUpperCase());
	}
	
	// In case of no txs (withdraw , deposit, funds transfer) ,
	// in 1 year , disable a/c status
	public static void disableAccounts(Map<Integer, BankAccount> acctMap) {
		// since it's searching based upon : value based criteria ,
		// Map ---> Collection<V> : values()
		for (BankAccount a : acctMap.values()) {
			long periodInMonths = Period.between(a.getLastTxDate(), LocalDate.now()).toTotalMonths();
			if (periodInMonths > 12)
				a.setActive(false);
		}
		System.out.println("a/c status updated by admin!");
		
	}
	//remove account inactive for 2 or more years
	public static void removeAccounts(Map<Integer, BankAccount> acctMap) {
		// since it's searching based upon : value based criteria ,
		// Map ---> Collection<V> : values()
		for (BankAccount a : acctMap.values()) {
			long periodInMonths = Period.between(a.getLastTxDate(), LocalDate.now()).toTotalMonths();
			if (periodInMonths > 24)
				acctMap.remove(a.getAcctNo(), a);
		}
	}
	public static BankAccount validateAll(int acctNo, String customerName, String acctType, double balance, String creationDate,Map<Integer, BankAccount>bank) throws InsufficientFundsException,DuplicateAccNo {
		AcType accType=parseAndValidateType(acctType);
		double balance1=validateBalance(accType, balance);
		int accNnumber=checkDuplicateAccNo(acctNo, bank);
		return new BankAccount(accNnumber, customerName, accType, balance1, LocalDate.parse(creationDate));
	}

}
