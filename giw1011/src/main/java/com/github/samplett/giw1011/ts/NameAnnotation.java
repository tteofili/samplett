

/* First created by JCasGen Tue Apr 05 11:32:00 CEST 2011 */
package com.github.samplett.giw1011.ts;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;


/** annotation for a first name
 * Updated by JCasGen Tue Apr 05 11:32:00 CEST 2011
 * XML source: /Users/tommasoteofili/Documents/workspaces/github/samplett/giw1011/src/main/resources/NameAnnotatorAEDescriptor.xml
 * @generated */
public class NameAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(NameAnnotation.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated  */
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected NameAnnotation() {}
    
  /** Internal - constructor used by generator 
   * @generated */
  public NameAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public NameAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public NameAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {}
     
}

    