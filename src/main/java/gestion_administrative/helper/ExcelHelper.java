package gestion_administrative.helper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gestion_administrative.entities.Discipline;
import gestion_administrative.entities.Etablissement;
import gestion_administrative.entities.NivDiscip;
import gestion_administrative.entities.Niveau;
import gestion_administrative.entities.Structure;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static List<Discipline> excelToDisciplines(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        List<Discipline> disciplines = new ArrayList<>();

        // Skip the first row
        if (iterator.hasNext()) {
            iterator.next(); // Skip the first row
        }

        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            Discipline discipline = new Discipline();

            discipline.setCodeDiscip(cellIterator.next().getStringCellValue());
            discipline.setNomDiscip(cellIterator.next().getStringCellValue());

            disciplines.add(discipline);
        }

        workbook.close();

        return disciplines;
    }
    
    public static List<Etablissement> excelToEtablissements(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        List<Etablissement> etablissements = new ArrayList<>();

        // Skip the first row
        if (iterator.hasNext()) {
            iterator.next(); // Skip the first row
        }

        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            Etablissement etablissement = new Etablissement();

            etablissement.setCodeEtab(cellIterator.next().getStringCellValue());
            etablissement.setNomEtab(cellIterator.next().getStringCellValue());

            etablissements.add(etablissement);
        }

        workbook.close();

        return etablissements;
    }
    
    public static List<Niveau> excelToNiveaux(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        List<Niveau> niveaux = new ArrayList<>();

        // Skip the first row
        if (iterator.hasNext()) {
            iterator.next(); // Skip the first row
        }

        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            Niveau niveau = new Niveau();

            niveau.setCodeNiv(cellIterator.next().getStringCellValue());
            niveau.setNomNiv(cellIterator.next().getStringCellValue());

            niveaux.add(niveau);
        }

        workbook.close();

        return niveaux;
    }

    public static List<Structure> excelToStructures(InputStream is) throws IOException {
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        List<Structure> structures = new ArrayList<>();

        // Skip the header row
        if (rows.hasNext()) {
            rows.next(); // Skip the header row
        }

        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // Read data from each cell in the row
            String codeStr = currentRow.getCell(0).getStringCellValue();
            String etablissementCode = currentRow.getCell(1).getStringCellValue();
            String etablissementNom = currentRow.getCell(2).getStringCellValue();
            String niveauCode = currentRow.getCell(3).getStringCellValue();
            int nbrClasse = (int) currentRow.getCell(4).getNumericCellValue();

            // Create a new Structure object
            Structure structure = new Structure();
            structure.setCodeStr(codeStr);
            structure.setNbrClasse(nbrClasse);

            // Fetch Niveau object from database based on niveauCode and set it to structure
            // Example: Niveau niveau = NiveauService.getByCode(niveauCode);structure.setNiveau(niveau);

            // Fetch Etablissement object from database based on etablissementCode and set it to structure
            // Example: Etablissement etablissement = EtablissementService.getByCode(etablissementCode);
            // structure.setEtablissement(etablissement);

            structures.add(structure);
        }

        workbook.close();

        return structures;
    }


	
	
	
	
    public static List<NivDiscip> excelToNivDiscip(InputStream is) throws IOException {
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        List<NivDiscip> nivDiscips = new ArrayList<>();

        // Skip the header row
        if (rows.hasNext()) {
            rows.next(); // Skip the header row
        }

        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // Read data from each cell in the row
            String codeNivDiscip = currentRow.getCell(0).getStringCellValue();
            int nbreHeures = (int) currentRow.getCell(3).getNumericCellValue(); // Assuming the HEUR_SEMAINE column is the fourth column (index 3)
            String disciplineCode = currentRow.getCell(2).getStringCellValue(); // Assuming the CD_DISCIP column is the third column (index 2)
            String niveauCode = currentRow.getCell(1).getStringCellValue(); // Assuming the CD_NIVEAU column is the second column (index 1)

            // Retrieve Discipline and Niveau objects by code
//            Discipline discipline = disciplineService.getByCode(disciplineCode);
//            Niveau niveau = niveauService.getByCode(niveauCode);

//            if (discipline != null && niveau != null) {
                // Create a new NivDiscip object
                NivDiscip nivDiscip = new NivDiscip();
                nivDiscip.setCodeNivDiscip(codeNivDiscip);
                nivDiscip.setNbreHeures(nbreHeures);
//                nivDiscip.setDiscipline(discipline);
//                nivDiscip.setNiveau(niveau);

                // Add the NivDiscip to the list
                nivDiscips.add(nivDiscip);
            }
        

        workbook.close();

        return nivDiscips;
    }

	
}

  
