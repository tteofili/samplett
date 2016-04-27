# Looseen

playing with Lucene stuff

## Evaluation of Lucene Classifiers on 20 Newsgroups dataset

 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.36961232843727426
    * precision = 0.4086339444115908
    * recall = 0.691
    * f1-measure = 0.5135637309550353
    * avgClassificationTime = 25.7515
    * time = 52 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.3729261841788892
    * precision = 0.41037735849056606
    * recall = 0.696
    * f1-measure = 0.516320474777448
    * avgClassificationTime = 26.0605
    * time = 52 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.20429329474191993
    * precision = 0.25953350610884857
    * recall = 0.3505
    * f1-measure = 0.2982344182088917
    * avgClassificationTime = 15.0645
    * time = 30 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Dirichlet(2000.000000)} 
    * accuracy = 0.42830009496676164
    * precision = 0.44320712694877507
    * recall = 0.796
    * f1-measure = 0.5693848354792561
    * avgClassificationTime = 25.53
    * time = 51 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Jelinek-Mercer(0.300000)} 
    * accuracy = 0.4338862559241706
    * precision = 0.4461368042093603
    * recall = 0.8055
    * f1-measure = 0.5742291926572803
    * avgClassificationTime = 24.1485
    * time = 48 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.4305687203791469
    * precision = 0.4439810953572422
    * recall = 0.7985
    * f1-measure = 0.5706628551009469
    * avgClassificationTime = 22.274
    * time = 45 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR GB1} 
    * accuracy = 0.42738490745135266
    * precision = 0.4424310008363535
    * recall = 0.7935
    * f1-measure = 0.5681045283694289
    * avgClassificationTime = 26.0575
    * time = 52 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR PL3(800.0)} 
    * accuracy = 0.4281984334203655
    * precision = 0.443052074631022
    * recall = 0.7955
    * f1-measure = 0.5691289572527276
    * avgClassificationTime = 26.3175
    * time = 53 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB SPL-D} 
    * accuracy = 0.3789173789173789
    * precision = 0.408983451536643
    * recall = 0.692
    * f1-measure = 0.5141158989598811
    * avgClassificationTime = 28.3
    * time = 57 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB LL-L1} 
    * accuracy = 0.43266951161688005
    * precision = 0.4455225949542556
    * recall = 0.8035
    * f1-measure = 0.5732120563581237
    * avgClassificationTime = 24.757
    * time = 50 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.38476190476190475
    * precision = 0.412960235640648
    * recall = 0.7034621174109383
    * f1-measure = 0.520415738678545
    * avgClassificationTime = 160.30757651781235
    * time = 320 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.3786869647954329
    * precision = 0.40807840807840806
    * recall = 0.68941294530858
    * f1-measure = 0.512686567164179
    * avgClassificationTime = 161.00150526843953
    * time = 321 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.4364024679639298
    * precision = 0.4470033296337403
    * recall = 0.8083291520321124
    * f1-measure = 0.5756655351080937
    * avgClassificationTime = 160.49272453587557
    * time = 321 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.43966745843230404
    * precision = 0.4494475138121547
    * recall = 0.8163572503763171
    * f1-measure = 0.5797256369143061
    * avgClassificationTime = 161.4390366281987
    * time = 322 (sec)

 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.43649844534800286
    * precision = 0.44586167800453513
    * recall = 0.7968591691995948
    * f1-measure = 0.5717920756088696
    * avgClassificationTime = 456.5790273556231
    * time = 902 (sec)
 
 * CachingNaiveBayesClassifier 
    * accuracy = 0.3978260869565217
    * precision = 0.42568586071751585
    * recall = 0.706
    * f1-measure = 0.5311265751363551
    * avgClassificationTime = 34.412
    * time = 69 (sec)
 
 * SimpleNaiveBayesClassifier 
    * accuracy = 0.39747939893359185
    * precision = 0.42537087496215564
    * recall = 0.7049673858504767
    * f1-measure = 0.5305891238670695
    * avgClassificationTime = 207.13998996487706
    * time = 413 (sec)
