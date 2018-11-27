package br.ufpe.cin.testes.temp;

import java.io.IOException;

import br.ufpe.cin.support.SubnetCheck;

public class OtherTests {
    public static void main(String args[]) throws InterruptedException, IOException {
	SubnetCheck vm1 = new SubnetCheck("192.168.137.2");
	System.out.println(vm1.isAlive());

    }
}
