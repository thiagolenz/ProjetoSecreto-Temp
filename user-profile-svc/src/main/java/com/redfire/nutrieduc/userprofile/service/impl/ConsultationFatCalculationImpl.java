package com.redfire.nutrieduc.userprofile.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.redfire.nutrieduc.entities.consultation.Consultation;
import com.redfire.nutrieduc.entities.user.UserInfoProfile;
import com.redfire.nutrieduc.userprofile.service.ConsultationFatCalculation;

public class ConsultationFatCalculationImpl implements
		ConsultationFatCalculation {

	@Override
	public double calculate(Consultation cons, UserInfoProfile infoProfile) {
		double pct = 5.6;
		double pcse = 9.4;
		//double pcb = 3.2;
		double pcsi = 5.3;
		double pcab = 11.2;
		double pcc = 9.4;
		//double pcax = 6.3;
		double pcp = 5.1;
		
		double percentualGorduraGuedesSiri = 0;
		double percentualGorduraJackPolloSiri = 0;
		double percentualGorduraDurninWomersley = 0;
		double idade = 25;
		
		
		/*
		 * Exemplo para calcular o percentual de gordura segundo Faulkner
		 * o cálculo seve tanto para mulheres quanto para homens
		 */
		double percentual = formulaFaulkner(pct, pcse, pcsi, pcab);
		
		System.out.println("Percentual de gordura segundo Faulkner: " + percentual);
		
		
		/*
		 * Exemplo para calcular o percentual de gordura segundo a formula de siri
		 * combinado com calculo de densidade corporal de guedes para HOMEM
		 */
		double densidadeCorporalGuedesHomem = densidadeCorporalGuedesParaHomens(pct, pcsi, pcab);
		
		System.out.println("Densidade corporal HOMEM segundo Guedes: " + densidadeCorporalGuedesHomem);
		
		percentualGorduraGuedesSiri = formulaSiri(densidadeCorporalGuedesHomem);
		
		System.out.println("Percentual de gordura HOMEM guedes + siri: " + percentualGorduraGuedesSiri);
		
		/*
		 * Exemplo para calcular o percentual de gordura segundo a formula de siri
		 * combinado com calculo de densidade corporal de guedes para MULHER
		 */
		double densidadeCorporalGuedesMulher = densidadeCorporalGuedesParaMulheres(pcc, pcsi, pcse);
		
		System.out.println("Densidade corporal MULHER segundo Guedes: " + densidadeCorporalGuedesMulher);
		
		percentualGorduraGuedesSiri = formulaSiri(densidadeCorporalGuedesMulher);
		
		System.out.println("Percentual de gordura MULHER guedes + siri: " + percentualGorduraGuedesSiri);
		 
		
		/*
		 * Exemplo para calcular o percentual de gordura segundo a formula de siri
		 * combinado com calculo de densidade corporal de Jackson e Pollock para HOMEM
		 */
		double densidadeCorporalJacksonPollockHomem = densidadeCorporalJacksonPollockHomem(pcp, pcab, pcc, idade);
		
		System.out.println("Densidade corporal HOMEM segundo Jackson e Pollock: " + densidadeCorporalJacksonPollockHomem);
		
		percentualGorduraJackPolloSiri = formulaSiri(densidadeCorporalJacksonPollockHomem);
		
		System.out.println("Percentual de gordura HOMEM Jackson e Pollock + siri: " + percentualGorduraJackPolloSiri);
		
		/*
		 * Exemplo para calcular o percentual de gordura segundo a formula de siri
		 * combinado com calculo de densidade corporal de Jackson e Pollock para MULHER 
		 */
		double densidadeCorporalJacksonPollockMulher = densidadeCorporalJacksonPollockMulher(pct, pcsi, pcc, idade);
		
		System.out.println("Densidade corporal HOMEM segundo Jackson e Pollock: " + densidadeCorporalJacksonPollockMulher);
		
		percentualGorduraJackPolloSiri = formulaSiri(densidadeCorporalJacksonPollockMulher);
		
		System.out.println("Percentual de gordura MULHER Jackson e Pollock + siri: " + percentualGorduraJackPolloSiri);
		
		
		
		
		/*
		 * Exemplo para caulcar o percentual de gordura segundo a formula de siri
		 * combinado com calculo de densidade corportal de Durnin Womers para MULHER
		 */
		double densidadeCorporalDurninWomersley =  densidadeCorporalDurninWomersleyMulher(7, 11, 12, 12, 45);
		
		System.out.println("Densidade corporal MHULHER segundo Durnin e Womersley: " + densidadeCorporalJacksonPollockMulher);
		
		percentualGorduraDurninWomersley = formulaSiri(densidadeCorporalDurninWomersley);
		
		System.out.println("Percentual de gordura MULHER Durnin e Womersley + siri: " + percentualGorduraDurninWomersley);
		
		
		/*
		 * Exemplo para caulcar o percentual de gordura segundo a formula de siri
		 * combinado com calculo de densidade corportal de Durnin Womers para HOMEM
		 */
		double densidadeCorporalDurninWomersleyHomem =  densidadeCorporalDurninWomersleyHomem(7, 11, 12, 12, 45);
		
		System.out.println("Densidade corporal HOMEM segundo Durnin e Womersley: " + densidadeCorporalDurninWomersleyHomem);
		
		percentualGorduraDurninWomersley = formulaSiri(densidadeCorporalDurninWomersleyHomem);
		
		System.out.println("Percentual de gordura HOMEM Durnin e Womersley + siri: " + percentualGorduraDurninWomersley);
		
		return densidadeCorporalDurninWomersleyHomem;
	}
	
	/**
	 * Protoloco de Faulkner para calcular o percentural de gordura,
	 * caso algum dos valores dos parametros seja zero, não aplicar,
	 * a formula é aplicada tanto para homens quanto para mulheres
	 * 
	 * @param pct - Trícipes
	 * @param pcse - Dobra de costa ou Subescapular
	 * @param pcsi - Dobra de cintura ou Suprailíaca
	 * @param pcab - Dobra abdominal ou Abdominal
	 * @return
	 */
	public double formulaFaulkner(double pct, double pcse, double pcsi, double pcab){
		
		double percentualGordura = 0;
		
		double somatorio = pct + pcse + pcsi + pcab;

		percentualGordura = 5.783 + 0.153 * (somatorio); 
		
		return percentualGordura;
	}
	
	/**
	 * Protocolo para calcular a densidade corporal para homens,
	 * segundo Guedes, caso algum valor desses seja zero, não fazer a chamada
	 * para este método
	 * 
	 * @param pct - Trícipes
	 * @param pcsi - Dobra de cintura ou Suprailíaca
	 * @param pcab - Dobra abdominal ou Abdominal
	 * @return
	 */
	public double densidadeCorporalGuedesParaHomens(double pct, double pcsi, double pcab){
		
		double densidadeCorporal = 0;
	
		double somatorio = pct + pcsi + pcab;
		
		densidadeCorporal = 1.17136 - 0.06706 * Math.log10(somatorio);  
		
		return densidadeCorporal;
	}
	
	/**
	 * Protocolo para calcular a densidade corporal para homens,
	 * segundo Guedes, caso algum valor desses seja zero, não fazer a chamada
	 * para este método
	 *  
	 * @param pcc - Coxa ou dobra de coxa
	 * @param pcsi - Dobra de cintura ou Suprailíaca
	 * @param pcse - Dobra de costa ou Subescapular
	 * @return
	 */
	public double densidadeCorporalGuedesParaMulheres(double pcc, double pcsi, double pcse){
		
		double densidadeCorporal = 0;
	
		double somatorio = pcc + pcsi + pcse;
		
		densidadeCorporal = 1.16650 - 0.07063 * Math.log10(somatorio);  
		
		return densidadeCorporal;
	}
	
	/**
	 * 
	 * Protocolo para encontrar a densidade corporal para homens entre 18 e 61 anos
	 * segundo Jackson e Pollock, caso algum valor desses seja zero não chamar o 
	 * método
	 * 
	 * @param pcp - Dobra de peitoral ou Peito
	 * @param pcab - Dobra abdominal ou Abdominal
	 * @param pcc  - Coxa ou dobra de coxa
	 * @param idade - idade da pessoa
	 * @return
	 */
	public double densidadeCorporalJacksonPollockHomem(double pcp, double pcab, double pcc, double idade){
		
		double densidadeCorporal = 0;
		
		double somatorio = 0;

		somatorio = pcp + pcab + pcc;

		densidadeCorporal =  1.1093800 - 0.0008267 * somatorio  + 0.0000016 * Math.pow(somatorio, 2) - 0.0002574 * idade;

		return densidadeCorporal;
	}
	
	/**
	 * 
	 * Protocolo para encontrar a densidade corporal para mulheres entre 18 e 55 anos
	 * segundo Jackson e Pollock, caso algum valor desses seja zero não chamar o 
	 * método
	 * 
	 * @param pct - Trícipes
	 * @param pcsi - Dobra de cintura ou Suprailíaca
	 * @param pcc - Dobra da coxa ou Coxa
	 * @param idade - idade da pessoa
	 * @return
	 */
	public double densidadeCorporalJacksonPollockMulher(double pct, double pcsi, double pcc, double idade){
		
		double densidadeCorporal = 0;
		
		double somatorio = 0;
		
		somatorio = pct + pcsi + pcc;
		
		densidadeCorporal =  1.0994921 - 0.0009929 * somatorio + 0.0000023 * Math.pow(somatorio, 2) - 0.0001392 * idade;

		return densidadeCorporal;
	}
	
	
	/**
	 * A formula de siri se calcula o percentual de gordura com base
	 * no valor da densidade corporal e também do sexo.
	 * Caso a densidade corporal seja zero, a formula não será aplicada
	 * 
	 * @param densidadeCorporal
	 * @param male
	 * @return
	 */
	public double formulaSiri(double densidadeCorporal){
		
		double percentualGordura = 0;
		
		percentualGordura = ( (4.95 / densidadeCorporal) - 4.50 ) * 100;
		
		BigDecimal bd = new BigDecimal(percentualGordura).setScale(2, RoundingMode.HALF_EVEN);
		
		return bd.doubleValue();
	}
	
	/**
	 * Protocolo de Durnin e Womersley para cálculo de 
	 * densidade corporal para MULHERES, caso algum valor passado como parametro
	 * seja zero, não chamar este método
	 * 
	 * @param pcb - Biceps
	 * @param pct - Trícipes
	 * @param pcse - Dobra de costa ou Subescapular
	 * @param pcsi - Dobra de cintura ou Suprailíaca
	 * @param idade
	 * @return
	 */
	public double densidadeCorporalDurninWomersleyMulher(double pcb, double pct, double pcse, double pcsi, double idade){
		
		double densidadeCorporal = 0;
		
		double somatorio = pcb+ pct + pcse + pcsi;
		
		if(idade < 17){
			
			densidadeCorporal =  1.1369 - (0.0598 * Math.log10(somatorio));
		}
		if(idade >=17 && idade <=19){
			
			densidadeCorporal = 1.1549 - (0.0678 * Math.log10(somatorio));
		}
		if(idade >=20 && idade <=29){

			densidadeCorporal =  1.1599 - (0.0717 * Math.log10(somatorio));
		}
		if(idade>=30 && idade<=39){
			
			densidadeCorporal =  1.1423 - (0.0632 * Math.log10(somatorio));
		}
		if(idade>=40 && idade<=49){
			
			densidadeCorporal =  1.1333 - (0.0612 * Math.log10(somatorio));
		}
		if(idade>=50){

			densidadeCorporal =  1.1339 - (0.0645 * Math.log10(somatorio));
		}
		
		return densidadeCorporal;
	}
	
	/**
	 * 
	 * Protocolo de Durnin e Womersley para cálculo de 
	 * densidade corporal para HOMENS, caso algum valor passado como parametro
	 * seja zero, não chamar este método
	 * 
	 * @param pcb - Biceps
	 * @param pct - Trícipes
	 * @param pcse - Dobra de costa ou Subescapular
	 * @param pcsi - Dobra de cintura ou Suprailíaca
	 * @param idade
	 * 
	 * @return
	 */
	public double densidadeCorporalDurninWomersleyHomem(double pcb, double pct, double pcse, double pcsi, double idade){
		
		double densidadeCorporal = 0;
		
		double somatorio = pcb+ pct + pcse + pcsi;
		
		if(idade < 17){
			
			densidadeCorporal = 1.1533 - (0.0643 * Math.log10(somatorio));
		}
		if(idade >=17 && idade <=19){
			
			densidadeCorporal = 1.1620 - (0.0630 * Math.log10(somatorio));
		}
		if(idade >=20 && idade <=29){

			densidadeCorporal = 1.1631 - (0.0632 * Math.log10(somatorio));
		}
		if(idade>=30 && idade<=39){
			
			densidadeCorporal = 1.1422 - (0.0544 * Math.log10(somatorio));
		}
		if(idade>=40 && idade<=49){
			
			densidadeCorporal = 1.1620 - (0.0700 * Math.log10(somatorio));
		}
		if(idade>=50){
			
			densidadeCorporal =  1.1715 - (0.0779 * Math.log10(somatorio));
		}
		
		return densidadeCorporal;
	}

}
