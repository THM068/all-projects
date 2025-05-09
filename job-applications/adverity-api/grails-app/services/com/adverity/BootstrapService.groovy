package com.adverity

import com.opencsv.CSVReader
import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class BootstrapService {

    void loadData() {
        Map <String, Campaign> campaignMap = [:]
        Map<String, DataSource> dataSourceMap = [:]

        def resource = this.class.classLoader.getResource('adverity.csv')
        try {
            resource.openStream().withStream  { inputStream ->
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream)
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
                 List<String[]> csvData = readAll(bufferedReader)

                csvData.eachWithIndex{ String[] entry, int i ->
                    if(i > 0) {
                        def (String datasource, String campaign, daily, clicks, impressions) = entry
                        DataSource dataSourceDomain = createDataSource(datasource, dataSourceMap)
                        Campaign campaignDomain = createCampaign(campaign, campaignMap)
                        def(month,day, year) = daily.split("/")
                        Date date = new SimpleDateFormat("MM/dd/yyyy").parse("$month/$day/${year.length() == 2 ? "20$year": year}")

                        new CampaignStat(
                                campaign: campaignDomain,
                                dataSource: dataSourceDomain,
                                daily: date,
                                clicks: clicks,
                                impressions: impressions ).save()
                    }
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace()
        }
        catch (Exception ex) {
            ex.printStackTrace()
        }

    }

    private List<String[]> readAll(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = new ArrayList<>();
        try {
            list = csvReader.readAll();
        }
        catch (Exception ex) {
            ex.printStackTrace()
        }
        finally {
            reader.close();
            csvReader.close();
        }
        return list;
    }

    private DataSource createDataSource(String sourceName,  Map<String, DataSource> dataSourceMap) {
        dataSourceMap.putIfAbsent(sourceName, new DataSource(name: sourceName).save())
    }

    private Campaign createCampaign(String campaignName, Map<String, Campaign> campaignMap) {
        campaignMap.putIfAbsent(campaignName, new Campaign(name: campaignName).save())
    }
 }
