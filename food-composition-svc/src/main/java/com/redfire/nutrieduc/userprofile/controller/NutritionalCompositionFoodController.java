package com.redfire.nutrieduc.userprofile.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.redfire.nutrieduc.commonsvc.response.Response;
import com.redfire.nutrieduc.commonsvc.svc.exception.BusinessException;
import com.redfire.nutrieduc.entities.food.FoodPreparationType;
import com.redfire.nutrieduc.entities.food.NutritionalCompositionFood;
import com.redfire.nutrieduc.entities.food.NutritionalFood;

public class NutritionalCompositionFoodController {
	DecimalFormat df = new DecimalFormat( "#,##0.00" ); 

	public Response loadIbgeFood () throws BusinessException {
		BufferedReader reader = readStreamFile("", "defaultData/fullTable_IBGE", ".csv");
		List<NutritionalCompositionFood> convertFoods = convertFoods(reader);
		System.out.println("total de " + convertFoods.size());
		return Response.newSuccessResponse();
	}
	
	private List<NutritionalCompositionFood> convertFoods(BufferedReader reader)
			throws BusinessException {
		List<NutritionalCompositionFood> resultList = new ArrayList<>();
		String line;
		
		try {
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(";");
				NutritionalFood food = new NutritionalFood();
				food.setCode(Long.valueOf (parts[0]));
				food.setName(parts[1]);
				
				FoodPreparationType foodPreparationType = new FoodPreparationType();
				foodPreparationType.setCode(Long.valueOf(parts[2]));
				foodPreparationType.setDescription(parts[3]);
				food.setReference(Integer.valueOf(parts[4]));
				food.setReferenceDescription(parts[5]);
				food.setFoodPreparationType(foodPreparationType);
				
				NutritionalCompositionFood compositionFood = createNutritionalCompositionFood(parts);
				compositionFood.setNutritionalFood(food);
				resultList.add(compositionFood);
			}
			return resultList;
		} catch (IOException e) {
			throw new BusinessException("error on import inputs");
		}
	}

	private NutritionalCompositionFood createNutritionalCompositionFood(String[] parts) {
		NutritionalCompositionFood compositionFood = new NutritionalCompositionFood();
		compositionFood.setEnergy(convertValue(parts[6]));
		compositionFood.setProtein(convertValue(parts[7]));
		compositionFood.setTotalLipid(convertValue(parts[8]));
		compositionFood.setCarbohydrate(convertValue(parts[9]));
		compositionFood.setTotalDietaryFiber(convertValue(parts[10]));
		compositionFood.setCalcium(convertValue(parts[11]));
		compositionFood.setMagnesium(convertValue(parts[12]));
		compositionFood.setManganese(convertValue(parts[13]));
		compositionFood.setPhosphorus(convertValue(parts[14]));
		compositionFood.setIron(convertValue(parts[15]));
		compositionFood.setSodium(convertValue(parts[16]));
		compositionFood.setAdditionOfSodium(convertValue(parts[17]));
		compositionFood.setPotassium(convertValue(parts[18]));
		compositionFood.setCopper(convertValue(parts[19]));
		compositionFood.setZinc(convertValue(parts[20]));
		compositionFood.setSelenium(convertValue(parts[21]));
		compositionFood.setRetinol(convertValue(parts[22]));
		compositionFood.setVitaminA(convertValue(parts[23]));
		compositionFood.setThiamine(convertValue(parts[24]));
		compositionFood.setRiboflavin(convertValue(parts[25]));
		compositionFood.setNiacin(convertValue(parts[26]));
		compositionFood.setNiacinEquivalent(convertValue(parts[27]));
		compositionFood.setPyridoxine(convertValue(parts[28]));
		compositionFood.setCobalamin(convertValue(parts[29]));
		compositionFood.setFolate(convertValue(parts[30]));
		compositionFood.setVitaminD(convertValue(parts[31]));
		compositionFood.setVitaminE(convertValue(parts[32]));
		compositionFood.setVitaminC(convertValue(parts[33]));
		compositionFood.setCholesterol(convertValue(parts[34]));
		compositionFood.setSaturatedFatty(convertValue(parts[35]));
		compositionFood.setFattyAcidsMonounsaturated(convertValue(parts[36]));
		compositionFood.setPolyunsaturatedFattyAcid(convertValue(parts[37]));
		compositionFood.setPolyunsaturatedFattyAcid18_2(convertValue(parts[38]));
		compositionFood.setPolyunsaturatedFattyAcid18_3(convertValue(parts[39]));
		compositionFood.setTransFattyTotal(convertValue(parts[40]));
		compositionFood.setTotalSugar(convertValue(parts[41]));
		compositionFood.setAddedSugar(convertValue(parts[42]));
		return compositionFood;
	}
	
	private BigDecimal convertValue (String value) {
		if (value != null && !value.equals("-")) {
			Number parse;
			try {
				parse = df.parse(value.trim());
				return new BigDecimal(parse.doubleValue());
			} catch (ParseException e) {
				System.out.println("erro ao parsear o valor: " + value);
				e.printStackTrace();
				System.exit(0);
			}
		}
		return null;
	}

	private BufferedReader readStreamFile(String country, String path, String fileExtension) {
		InputStream is = NutritionalCompositionFoodController.class.getClassLoader().getResourceAsStream(path + country + fileExtension);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}
	
	public static void main(String[] args) throws BusinessException {
		new NutritionalCompositionFoodController().loadIbgeFood();
	}
}
