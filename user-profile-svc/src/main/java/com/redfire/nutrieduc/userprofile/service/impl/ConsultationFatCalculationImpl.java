package com.redfire.nutrieduc.userprofile.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.redfire.nutrieduc.entities.consultation.Consultation;
import com.redfire.nutrieduc.entities.consultation.ConsultationBodyMeasure;
import com.redfire.nutrieduc.entities.user.GenderType;
import com.redfire.nutrieduc.entities.user.UserInfoProfile;
import com.redfire.nutrieduc.userprofile.service.ConsultationFatCalculation;

@Service
public class ConsultationFatCalculationImpl implements ConsultationFatCalculation {

	@Override
	public void calculate(Consultation cons, UserInfoProfile infoProfile) {
		ConsultationBodyMeasure bodyMeasure = cons.getBodyMeasure();
		BigDecimal pct = bodyMeasure.getBendingTriceps();
		BigDecimal pcb = bodyMeasure.getBendingBiceps();
		BigDecimal pcse = bodyMeasure.getBendingBack();
		BigDecimal pcsi = bodyMeasure.getFoldBelt();
		BigDecimal pcab = bodyMeasure.getAbdominalTuck();
		BigDecimal pcc = bodyMeasure.getBendingThigh();
		BigDecimal pcp = bodyMeasure.getPectoralFold();
		
		Long idade = infoProfile.getAge();

		calculateFaulkner(bodyMeasure, pct, pcse, pcsi, pcab);
		calculateGuedes(infoProfile, bodyMeasure, pct, pcse, pcsi, pcab, pcc);
		calculateJackson(infoProfile, bodyMeasure, pct, pcsi, pcab, pcc, pcp, idade);


		calculateDurnin(infoProfile, bodyMeasure, pct, pcb, pcse, pcsi);
	}

	private void calculateDurnin(UserInfoProfile infoProfile,
			ConsultationBodyMeasure bodyMeasure, BigDecimal pct,
			BigDecimal pcb, BigDecimal pcse, BigDecimal pcsi) {
		if (allValuesAreValid(pcb, pct, pcse, pcsi) && infoProfile.getGenderType() == GenderType.MALE) {
			BigDecimal densidadeCorporalDurninWomersleyHomem = densidadeCorporalDurninWomersleyHomem(pcb, pct, pcse, pcsi, 45);
			bodyMeasure.setDurninWomersleyFatPercent(formulaSiri(densidadeCorporalDurninWomersleyHomem));
		} else if (allValuesAreValid(pcb, pct, pcse, pcsi)) {
			BigDecimal densidadeCorporalDurninWomersley = densidadeCorporalDurninWomersleyMulher(pcb, pct, pcse, pcsi, 45);
			bodyMeasure.setDurninWomersleyFatPercent(formulaSiri(densidadeCorporalDurninWomersley));
		}
	}

	private void calculateJackson(UserInfoProfile infoProfile,
			ConsultationBodyMeasure bodyMeasure, BigDecimal pct,
			BigDecimal pcsi, BigDecimal pcab, BigDecimal pcc, BigDecimal pcp,
			Long idade) {
		if (allValuesAreValid(pcp, pcab, pcc) && infoProfile.getGenderType() == GenderType.MALE) {
			BigDecimal densidadeCorporalJacksonPollockHomem = densidadeCorporalJacksonPollockHomem(pcp, pcab, pcc, idade);
		    bodyMeasure.setJacksonPolloFatPercent(formulaSiri(densidadeCorporalJacksonPollockHomem));
		} else if (allValuesAreValid(pct, pcsi, pcc)) {
			BigDecimal densidadeCorporalJacksonPollockMulher = densidadeCorporalJacksonPollockMulher(pct, pcsi, pcc, idade);
			bodyMeasure.setJacksonPolloFatPercent(formulaSiri(densidadeCorporalJacksonPollockMulher));
		}
	}

	private void calculateGuedes(UserInfoProfile infoProfile,
			ConsultationBodyMeasure bodyMeasure, BigDecimal pct,
			BigDecimal pcse, BigDecimal pcsi, BigDecimal pcab, BigDecimal pcc) {
		if (allValuesAreValid(pct, pcsi, pcab) && infoProfile.getGenderType() == GenderType.MALE) {
			BigDecimal densidadeCorporalGuedesHomem = densidadeCorporalGuedesParaHomens(pct, pcsi, pcab);
			bodyMeasure.setGuedesFatPercent(formulaSiri(densidadeCorporalGuedesHomem));
		} else if (allValuesAreValid(pcc, pcsi, pcse)){
			BigDecimal densidadeCorporalGuedesMulher = densidadeCorporalGuedesParaMulheres(pcc, pcsi, pcse);
			bodyMeasure.setGuedesFatPercent(formulaSiri(densidadeCorporalGuedesMulher));
		}
	}

	private void calculateFaulkner(ConsultationBodyMeasure bodyMeasure,
			BigDecimal pct, BigDecimal pcse, BigDecimal pcsi, BigDecimal pcab) {
		/*
		 * Exemplo para calcular o percentual de gordura segundo Faulkner
		 * o cálculo seve tanto para mulheres quanto para homens
		 */
		if (allValuesAreValid(pct, pcse, pcsi, pcab))		
			bodyMeasure.setFaulknerFatPercent(formulaFaulkner(pct, pcse, pcsi, pcab));
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
	public BigDecimal formulaFaulkner(BigDecimal pct, BigDecimal pcse, BigDecimal pcsi, BigDecimal pcab){

		BigDecimal percentualGordura = BigDecimal.ZERO;

		BigDecimal sumarize = pct.add(pcse).add(pcsi).add(pcab);

		BigDecimal multiplicand = new BigDecimal(5.783 + 0.153);
		percentualGordura = sumarize.multiply(multiplicand) ; 

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
	public BigDecimal densidadeCorporalGuedesParaHomens(BigDecimal pct, BigDecimal pcsi, BigDecimal pcab){
		BigDecimal somatorio = pct.add(pcsi).add(pcab);
		BigDecimal multiplicand = new BigDecimal(Math.log10(somatorio.doubleValue())); 
		BigDecimal densidadeCorporal = new BigDecimal(1.17136 - 0.06706).multiply(multiplicand);  	
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
	public BigDecimal densidadeCorporalGuedesParaMulheres(BigDecimal pcc, BigDecimal pcsi, BigDecimal pcse){
		BigDecimal somatorio = pcc.add(pcsi).add(pcse);
		BigDecimal multiplicand = new BigDecimal(Math.log10(somatorio.doubleValue())); 
		BigDecimal densidadeCorporal = new BigDecimal(1.16650 - 0.07063).multiply(multiplicand);  

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
	public BigDecimal densidadeCorporalJacksonPollockHomem(BigDecimal pcp, BigDecimal pcab, BigDecimal pcc, long idade){

		BigDecimal sumarize = pcp.add(pcab).add(pcc);

		BigDecimal densidadeCorporal = new BigDecimal(1.1093800 - 0.0008267).multiply(sumarize);
		densidadeCorporal = densidadeCorporal.add(new BigDecimal(0.0000016 * Math.pow(sumarize.doubleValue(), 2) - 0.0002574));
		densidadeCorporal = densidadeCorporal.multiply(new BigDecimal(idade));

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
	public BigDecimal densidadeCorporalJacksonPollockMulher(BigDecimal pct, BigDecimal pcsi, BigDecimal pcc, long idade){

		BigDecimal sumarize = pct.add(pcsi).add(pcc);
		
		BigDecimal densidadeCorporal = new BigDecimal(1.0994921 - 0.0009929).multiply(sumarize);
		densidadeCorporal = densidadeCorporal.add(new BigDecimal(0.0000023 * Math.pow(sumarize.doubleValue(), 2) - 0.0001392));
		densidadeCorporal = densidadeCorporal.multiply(new BigDecimal(idade));
		
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
	public BigDecimal formulaSiri(BigDecimal densidadeCorporal){
		BigDecimal percentualGordura = new BigDecimal(( (4.95 / densidadeCorporal.doubleValue()) - 4.50 ) * 100);
		return percentualGordura.setScale(2, RoundingMode.HALF_EVEN);
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
	public BigDecimal densidadeCorporalDurninWomersleyMulher(BigDecimal pcb, BigDecimal pct, BigDecimal pcse, BigDecimal pcsi, long idade){

		BigDecimal densidadeCorporal = BigDecimal.ZERO;

		BigDecimal somatorio = pcb.add(pct).add(pcse).add(pcsi);

		if (idade < 17) {
			densidadeCorporal = new BigDecimal(1.1369).subtract(new BigDecimal(0.0598 * Math.log10(somatorio.doubleValue())));
		} else if(idade >= 17 && idade <= 19) {
			densidadeCorporal = new BigDecimal(1.1549).subtract(new BigDecimal(0.0678 * Math.log10(somatorio.doubleValue())));
		} else if (idade >= 20 && idade <= 29) {
			densidadeCorporal = new BigDecimal(1.1599).subtract(new BigDecimal(0.0717 * Math.log10(somatorio.doubleValue())));
		} else if(idade >= 30 && idade <= 39) {
			densidadeCorporal = new BigDecimal(1.1423).subtract(new BigDecimal(0.0632 * Math.log10(somatorio.doubleValue())));
		} else if (idade >= 40 && idade <= 49) {
			densidadeCorporal = new BigDecimal(1.1333).subtract(new BigDecimal(0.0612 * Math.log10(somatorio.doubleValue())));
		} else if (idade >= 50) {
			densidadeCorporal = new BigDecimal(1.1339).subtract(new BigDecimal(0.0645 * Math.log10(somatorio.doubleValue())));
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
	public BigDecimal densidadeCorporalDurninWomersleyHomem(BigDecimal pcb, BigDecimal pct, BigDecimal pcse, BigDecimal pcsi, long idade){

		BigDecimal densidadeCorporal = BigDecimal.ZERO;

		BigDecimal somatorio = pcb.add(pct).add(pcse).add(pcsi);

		if (idade < 17) {
			densidadeCorporal = new BigDecimal(1.1533).subtract(new BigDecimal(0.0643 * Math.log10(somatorio.doubleValue())));
		} else if (idade >= 17 && idade <= 19) {
			densidadeCorporal = new BigDecimal(1.1620).subtract(new BigDecimal(0.0630 * Math.log10(somatorio.doubleValue())));
		} else if (idade >= 20 && idade <= 29) {
			densidadeCorporal = new BigDecimal(1.1631).subtract(new BigDecimal(0.0632 * Math.log10(somatorio.doubleValue())));
		} else if (idade >= 30 && idade <= 39) {
			densidadeCorporal = new BigDecimal(1.1422).subtract(new BigDecimal(0.0544 * Math.log10(somatorio.doubleValue())));
		} else if (idade >= 40 && idade <= 49) {
			densidadeCorporal = new BigDecimal(1.1620).subtract(new BigDecimal(0.0700 * Math.log10(somatorio.doubleValue())));
		} else if (idade >= 50) {
			densidadeCorporal = new BigDecimal(1.1715).subtract(new BigDecimal(0.0779 * Math.log10(somatorio.doubleValue())));
		}

		return densidadeCorporal;
	}

	private boolean allValuesAreValid (BigDecimal ... values){
		for (BigDecimal value : values) {
			if (value == null || value.equals(BigDecimal.ZERO))
				return false;
		}
		return true;
	}

}
