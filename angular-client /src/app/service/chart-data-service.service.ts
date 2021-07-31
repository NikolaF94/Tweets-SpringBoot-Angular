import { Injectable } from '@angular/core';
// import * as Highcharts from 'highcharts';
import * as Highcharts from 'highcharts/highstock';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { AuthenticationService } from '../service/authentication.service';
import * as moment from 'moment';
// require('highcharts/highcharts-more.js')(Highcharts);






@Injectable({
  providedIn: "root",
})
export class ChartDataService {
  charts = [];

  constructor(private http: HttpClient) {}

  // createChart(container, symbol, data = null, sentimentData = null)
  // {
  //   let e = document.createElement("div");

  //   container.appendChild(e);

  //   let options: any = this.transformConfiguration(symbol, data, sentimentData);

  //   if(options.chart != null)
  //   {
  //     options.chart['renderTo'] = e;
  //   }
  //   else
  //   {
  //     options.chart = {
  //       'renderTo': e
  //     };
  //   }

  //   this.charts.push(new Highcharts.Chart(options));
  // }

  createChart(container, index) {
    let e = document.createElement("div");

    container.appendChild(e);

    let options: any = this.transformConfiguration(index);

    if (options.chart != null) {
      options.chart["renderTo"] = e;
    } else {
      options.chart = {
        renderTo: e,
      };
    }

    this.charts.push(new Highcharts.Chart(options));
  }

  getCharts() {
    return this.charts;
  }

  /**
   * Retrieve Chart Intra Day
   */
  chartIntraDay(index) {
    var config = {
      chart: { zoomType: "xy" },
      title: { text: index },
      xAxis: {
        type: "datetime",
      },
      
     
      series: [],
    
    };

    return config;
  }

  transformConfiguration(index) {
    let chartConfig = this.chartIntraDay(index);

    return chartConfig;
  }

  loadData(index, callback) {
    let username = sessionStorage.getItem("username");
    let password = sessionStorage.getItem("password");

    const headers = new HttpHeaders({
      Authorization: "Basic " + btoa(username + ":" + password),
    });
    this.http
      .post("http://localhost:8080/search/" + index, index, { headers })
      .subscribe(this.onDataReceived.bind(this, index, callback));
  }

  onDataReceived(index, callback, rawData) {
    var highchartsData = this.transformDataForHighCharts(rawData);

    callback(index, highchartsData);
  }

  loadSentimentData(keyword, callback) {
    let username = sessionStorage.getItem("username");
    let password = sessionStorage.getItem("password");

    const headers = new HttpHeaders({
      Authorization: "Basic " + btoa(username + ":" + password),
    });

    this.http
      .post("http://localhost:8080/search/" + keyword + "/15", keyword, {
        headers,
      })
      .subscribe(this.onSentimentDataReceived.bind(this, keyword, callback));
  }

  onSentimentDataReceived(keyword, callback, rawSentimentData) {
    var highchartsSentimentData =
      this.transformSentimentDataForHighCharts(rawSentimentData);

    callback(keyword, highchartsSentimentData);
  }

  transformDataForHighCharts(rawData) {
    var quotes = rawData["stockUnits"],
      data = [],
      i,
      item;

    for (var each in quotes) {
      item = quotes[each];
      data.push([
        moment(item["date"]).unix() * 1000,
        // data.push([new Date(item["date"]),
        parseFloat(item["close"]),
      ]);
    }
    data = data.slice(0, 6);
    return data.reverse();
  }
  transformSentimentDataForHighCharts(rawSentimentData) {
    var quotes = rawSentimentData,
      sentimentData = [],
      i,
      item;

    for (var key in quotes) {
      if (quotes.hasOwnProperty(key)) {
        sentimentData.push([
          moment(quotes[key].createdAt).unix() * 1000,
          parseFloat(quotes[key].sentimentType),
        ]);
        // sentimentData.push([new Date(new Date(quotes[key].createdAt)), parseFloat(quotes[key].sentimentType)])
      }
    }

    return sentimentData;

    // var quotes = rawSentimentData,
    //   sentimentData = [],
    //   i, item;

    // for (var each in quotes)
    // {
    //   item = quotes[each];

    //   sentimentData.push([new Date(item["closedAt"]),
    //     parseFloat(item["sentimentType"])]);
    // }

    // return sentimentData;
  }

  // dateParser () {
  //  var dStr ="2021-06-17T17:54:33.000+0000" ;
  //  dStr = dStr.replace("+0000 ", "") + " UTC";
  //  var parsedDate = new Date(dStr);
  //  return parsedDate;

  addDataSets(index, data) {
    this.charts[0].addSeries({
      id: "index",
      name: index,
      type: "spline",
      data: data,
    });
  }

  addSentimentDataSets(keyword, data) {
    let sentimentArray = [
      "- -",
      "-",
      "N",
      "+",
      "+ +",
    ];
    let sentimentNameArray = [ 
      "Tweet sentiment: Very negative",
      "Tweet sentiment: Negative",
      "Tweet sentiment: Neutral",
      "Tweet sentiment: Positive",
      "Tweet sentiment: Very Positive",
    ];

    data.forEach((value, index) => {
      data[index] = {
        x: value[0],
        title: sentimentArray[value[1]],
        text: sentimentNameArray[value[1]],
        
      };
    });

    console.log(data);
    this.charts[0].addSeries({
      type: "flags",
      color:  "red",
      data: data,
      name: "Tweet Sentiment Analysis",
      onSeries: "index",  
      shape: "squarepin",
      width: 16,
    });
  }
}


