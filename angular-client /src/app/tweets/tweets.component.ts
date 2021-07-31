import { Component, OnInit, AfterViewInit, OnDestroy, ViewChild, ElementRef  } from '@angular/core';
import { HttpClientService} from '../service/httpclient.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TweetService } from '../services/tweet.service';
import { Keyword } from '../classes/keyword';
import { IndexList } from '../classes/index-list';
import { Index } from "../classes/index";
import { Tweet } from "../classes/tweet";
import { ChartDataService } from '../service/chart-data-service.service';
import * as Highcharts from 'highcharts';
    
@Component({
selector: 'app-tweeets',
templateUrl: './tweets.component.html',
styleUrls: ['./tweets.component.css']
})
export class TweetsComponent implements OnInit, AfterViewInit, OnDestroy  {
index : Index;
keyword : Keyword;
my_data: any;
tweets: any;
indexName : String;





@ViewChild('charts') public chartEl: ElementRef;
selectedIndex?: Index;
indexlist = IndexList;

constructor(
private httpClientService: HttpClientService,
private route: ActivatedRoute,
private router: Router,
private hcs: ChartDataService,

) 


{ this.keyword = new Keyword();
  this.index = new Index();
  
  

}





onSubmit() {
this.httpClientService.sendKeyword(this.keyword).subscribe(result =>  this.tweets = result);
this.createChart(this.keyword.text, this.selectedIndex.name);

}

ngOnInit () {


}


ngAfterViewInit() {}


ngOnDestroy() {}



onSelect(index: Index ): void {
  this.selectedIndex = index;
  console.log(index.name);
  
  



  
  
  
}

handleSuccessfulResponse(response)
{

}

// createChart()
//   {
//     this.hcs.loadData("TSLA", (symbol, data, sentimentData)=> {
//       this.hcs.createChart(this.chartEl.nativeElement, symbol, data, sentimentData );
//     });
//   }
// createChart(keyword, index) {
//     this.hcs.loadSentimentData(keyword, (keyword, data) => {
//         this.hcs.addSentimentDataSets(keyword, data);
//     });
//     this.hcs.createChart(this.chartEl.nativeElement, index);
//     this.hcs.loadData(index, (index, data) => {
//         this.hcs.addDataSets(index, data);
//     });
// }

createChart(keyword, index) {
  this.hcs.loadData(index, (index, data) => {
        this.hcs.addDataSets(index, data);
    
    });
    this.hcs.createChart(this.chartEl.nativeElement, index);
    this.hcs.loadSentimentData(keyword, (keyword, data) => {
        this.hcs.addSentimentDataSets(keyword, data);
    
    });
}


}


