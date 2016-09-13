# Looseen

playing with Lucene stuff

## Evaluation of Lucene Classifiers on 20 Newsgroups dataset

 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.4945364821995065
    * precision = 0.6615133008363202
    * recall = 0.6415
    * f1-measure = 0.6513529558203736
    * avgClassificationTime = 42.248
    * time = 138 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.5467909645034063
    * precision = 0.7029182263932785
    * recall = 0.6839999999999999
    * f1-measure = 0.6933300863791036
    * avgClassificationTime = 42.587
    * time = 139 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.5150869719559815
    * precision = 0.6744788668277342
    * recall = 0.6585
    * f1-measure = 0.6663936613834724
    * avgClassificationTime = 43.0
    * time = 139 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Dirichlet(2000.000000)} 
    * accuracy = 0.6877862595419847
    * precision = 0.8005334847874422
    * recall = 0.7955000000000001
    * f1-measure = 0.7980088052265668
    * avgClassificationTime = 44.885
    * time = 143 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=LM Jelinek-Mercer(0.300000)} 
    * accuracy = 0.7025680337293982
    * precision = 0.8082439960733113
    * recall = 0.806
    * f1-measure = 0.8071204383225141
    * avgClassificationTime = 42.6465
    * time = 139 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.6934807472359893
    * precision = 0.8006648384042769
    * recall = 0.799
    * f1-measure = 0.7998315528685024
    * avgClassificationTime = 38.679
    * time = 131 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR GB1} 
    * accuracy = 0.6858120958539369
    * precision = 0.7957396713915428
    * recall = 0.7934999999999999
    * f1-measure = 0.7946182575423838
    * avgClassificationTime = 45.194
    * time = 144 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=DFR PL3(800.0)} 
    * accuracy = 0.6882621951219512
    * precision = 0.7984839968403343
    * recall = 0.7955000000000001
    * f1-measure = 0.7969892053440883
    * avgClassificationTime = 45.86
    * time = 145 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB SPL-D} 
    * accuracy = 0.5649097983728334
    * precision = 0.7459576338968372
    * recall = 0.6924999999999998
    * f1-measure = 0.7182354895974744
    * avgClassificationTime = 48.34
    * time = 150 (sec)
 
 * KNearestNeighborClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=IB LL-L1} 
    * accuracy = 0.7001533742331288
    * precision = 0.806105817794377
    * recall = 0.8045
    * f1-measure = 0.805302108375186
    * avgClassificationTime = 43.321
    * time = 140 (sec)
 
 * MinHashClassifier{min=15, hashCount=1, hashSize=100} 
    * accuracy = 0.7251051893408135
    * precision = 0.7925096044794668
    * recall = 0.7960928206072884
    * f1-measure = 0.7942971714322676
    * avgClassificationTime = 275.2777179763186
    * time = 309 (sec)
 
 * MinHashClassifier{min=30, hashCount=3, hashSize=300} 
    * accuracy = 0.7001034126163392
    * precision = 0.7280253955101635
    * recall = 0.7251690401877424
    * f1-measure = 0.7265944106658508
    * avgClassificationTime = 1192.5853658536585
    * time = 640 (sec)

 * MinHashClassifier{min=10, hashCount=1, hashSize=100} 
    * accuracy = 0.7701277068295391
    * precision = 0.8479937273199326
    * recall = 0.845846737404352
    * f1-measure = 0.8469188716774162
    * avgClassificationTime = 157.30441518202943
    * time = 217 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=LM Jelinek-Mercer(0.300000)} 
    * accuracy = 0.5187607573149742
    * precision = 0.7329905695421368
    * recall = 0.6492894248608535
    * f1-measure = 0.6886058211845795
    * avgClassificationTime = 241.3677872553939
    * time = 494 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=IB LL-L1} 
    * accuracy = 0.5280665280665281
    * precision = 0.7346825328324595
    * recall = 0.6583300350443209
    * f1-measure = 0.6944138032052934
    * avgClassificationTime = 242.06823883592574
    * time = 495 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.6752851711026616
    * precision = 0.7894131101641592
    * recall = 0.7858694083694082
    * f1-measure = 0.7876372733714722
    * avgClassificationTime = 233.15955845459106
    * time = 476 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=ClassicSimilarity} 
    * accuracy = 0.7001153402537486
    * precision = 0.8081116860303522
    * recall = 0.804501752216038
    * f1-measure = 0.8063026785943755
    * avgClassificationTime = 233.46312092323132
    * time = 477 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=1, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.6887189292543021
    * precision = 0.7975274982047024
    * recall = 0.795797876726448
    * f1-measure = 0.7966617486772696
    * avgClassificationTime = 234.20170597089813
    * time = 479 (sec)
 
 * FuzzyLikeThisClassifier{textFieldNames=[body], classFieldName='category', k=3, query=null, similarity=BM25(k1=1.2,b=0.75)} 
    * accuracy = 0.7028483448806775
    * precision = 0.8081390656471626
    * recall = 0.8063642547928263
    * f1-measure = 0.8072506847021592
    * avgClassificationTime = 234.59759157049675
    * time = 480 (sec)
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=1} 
    * accuracy = 0.7309201918883559
    * precision = 0.7962437894373874
    * recall = 0.8168508887276783
    * f1-measure = 0.8064157124189191
    * avgClassificationTime = 1016.8769751693002
    * time = 1831 (sec)
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=2} 
    * accuracy = 0.734375
    * precision = 0.787689655257609
    * recall = 0.8019603262687807
    * f1-measure = 0.7947609351366495
    * avgClassificationTime = 1401.9828793774318
    * time = 1832 (sec)
 
 * BM25NBClassifier{similarity=BM25(k1=1.2,b=0.75), ngramSize=3} 
    * accuracy = 0.7375713121434393
    * precision = 0.8160048156752309
    * recall = 0.7967147663171397
    * f1-measure = 0.8062444249993956
    * avgClassificationTime = 1753.3268482490273
    * time = 1831 (sec)
 
 * org.apache.lucene.classification.CachingNaiveBayesClassifier@32871e2 
    * accuracy = 0.6037390029325513
    * precision = 0.7464060413775376
    * recall = 0.706
    * f1-measure = 0.7256409711883911
    * avgClassificationTime = 27.7965
    * time = 69 (sec)
 
 * org.apache.lucene.classification.SimpleNaiveBayesClassifier@4ca030 
    * accuracy = 0.602436323366556
    * precision = 0.7455189876733195
    * recall = 0.7053601596051811
    * f1-measure = 0.7248837963111276
    * avgClassificationTime = 350.53000504286433
    * time = 707 (sec)
