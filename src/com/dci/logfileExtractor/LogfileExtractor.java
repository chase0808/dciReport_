package com.dci.logfileExtractor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LogfileExtractor {

	public static void main(String[] args) {
		try {
			FileInputStream fstream = new FileInputStream(
					"access_log.Q111041100");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fstream));
			String strLine;
			strLine = br.readLine();
			System.out.println(strLine);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
