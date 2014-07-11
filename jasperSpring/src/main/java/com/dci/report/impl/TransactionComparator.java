package com.dci.report.impl;

import java.util.Comparator;

import com.dci.report.bean.Transaction;

public class TransactionComparator implements Comparator<Transaction> {

	@Override
	public int compare(Transaction t1, Transaction t2) {
		int tid1 = t1.getId();
		int tid2 = t2.getId();
		return tid1 - tid2;
	}

}
