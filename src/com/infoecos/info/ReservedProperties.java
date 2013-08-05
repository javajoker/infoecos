package com.infoecos.info;


/**
 * http://www.w3.org/TR/owl2-primer/
 * 
 * @author Ning
 * 
 */
public class ReservedProperties {
	/**
	 * SubClassOf( :Woman :Person )
	 */
	public static final InfoProperty SubClassOf = new InfoProperty(0x00);
	/**
	 * EquivalentClasses( :Person :Human )
	 * 
	 * EquivalentClasses( :Mother ObjectIntersectionOf( :Woman :Parent ) )
	 * 
	 * EquivalentClasses( :ChildlessPerson ObjectIntersectionOf( :Person
	 * ObjectComplementOf( :Parent ) ) )
	 * 
	 * SubClassOf( :Grandfather ObjectIntersectionOf( :Man :Parent ) )
	 * 
	 * EquivalentClasses( :HappyPerson ObjectAllValuesFrom( :hasChild
	 * :HappyPerson ) )
	 */
	public static final InfoProperty EquivalentClasses = new InfoProperty(0x00);
	/**
	 * EquivalentClasses( :Parent ObjectUnionOf( :Mother :Father ) )
	 */
	public static final InfoProperty ObjectUnionOf = new InfoProperty(0x00);
	/**
	 * ClassAssertion( ObjectIntersectionOf( :Person ObjectComplementOf( :Parent
	 * ) ) :Jack )
	 */
	public static final InfoProperty ObjectComplementOf = new InfoProperty(0x00);
	/**
	 * EquivalentClasses( :MyBirthdayGuests ObjectOneOf( :Bill :John :Mary) )
	 */
	public static final InfoProperty ObjectOneOf = new InfoProperty(0x00);
	/**
	 * EquivalentClasses( :HappyPerson ObjectIntersectionOf(
	 * ObjectAllValuesFrom( :hasChild :HappyPerson ) ObjectSomeValuesFrom(
	 * :hasChild :HappyPerson ) ) )
	 */
	public static final InfoProperty ObjectIntersectionOf = new InfoProperty(
			0x00);
	/**
	 * EquivalentClasses( :Parent ObjectSomeValuesFrom( :hasChild :Person ) )
	 */
	public static final InfoProperty ObjectSomeValuesFrom = new InfoProperty(
			0x00);
	/**
	 * EquivalentClasses( :JohnsChildren ObjectHasValue( :hasParent :John ) )
	 */
	public static final InfoProperty ObjectHasValue = new InfoProperty(0x00);
	/**
	 * EquivalentClasses( :NarcisticPerson ObjectHasSelf( :loves ) )
	 */
	public static final InfoProperty ObjectHasSelf = new InfoProperty(0x00);
	/**
	 * ClassAssertion( ObjectMaxCardinality( 4 :hasChild :Parent ) :John )
	 */
	public static final InfoProperty ObjectMaxCardinality = new InfoProperty(
			0x00);
	/**
	 * ClassAssertion( ObjectMinCardinality( 2 :hasChild :Parent ) :John )
	 */
	public static final InfoProperty ObjectMinCardinality = new InfoProperty(
			0x00);
	/**
	 * ClassAssertion( ObjectExactCardinality( 3 :hasChild :Parent ) :John )
	 * 
	 * ClassAssertion( ObjectExactCardinality( 5 :hasChild ) :John )
	 */
	public static final InfoProperty ObjectExactCardinality = new InfoProperty(
			0x00);

	/**
	 * DisjointClasses( :Woman :Man )
	 */
	public static final InfoProperty DisjointClasses = new InfoProperty(0x00);

	/**
	 * SubObjectPropertyOf( :hasWife :hasSpouse )
	 */
	public static final InfoProperty SubObjectPropertyOf = new InfoProperty(
			0x00);
	/**
	 * ObjectPropertyDomain( :hasWife :Man )
	 */
	public static final InfoProperty ObjectPropertyDomain = new InfoProperty(
			0x00);
	/**
	 * ObjectPropertyRange( :hasWife :Woman )
	 */
	public static final InfoProperty ObjectPropertyRange = new InfoProperty(
			0x00);
	/**
	 * DifferentIndividuals( :John :Bill )
	 */
	public static final InfoProperty DifferentIndividuals = new InfoProperty(
			0x00);
	/**
	 * SameIndividual( :James :Jim )
	 */
	public static final InfoProperty SameIndividual = new InfoProperty(0x00);

	/**
	 * DataPropertyDomain( :hasAge :Person )
	 */
	public static final InfoProperty DataPropertyDomain = new InfoProperty(0x00);
	/**
	 * DataPropertyRange( :hasAge xsd:nonNegativeInteger )
	 */
	public static final InfoProperty DataPropertyRange = new InfoProperty(0x00);
	/**
	 * InverseObjectProperties( :hasParent :hasChild )
	 */
	public static final InfoProperty InverseObjectProperties = new InfoProperty(
			0x00);
	/**
	 * EquivalentClasses( :Orphan ObjectAllValuesFrom( ObjectInverseOf(
	 * :hasChild ) :Dead ) )
	 */
	public static final InfoProperty ObjectAllValuesFrom = new InfoProperty(
			0x00);
	public static final InfoProperty ObjectInverseOf = new InfoProperty(0x00);
	/**
	 * SymmetricObjectProperty( :hasSpouse )
	 */
	public static final InfoProperty SymmetricObjectProperty = new InfoProperty(
			0x00);
	/**
	 * AsymmetricObjectProperty( :hasChild )
	 */
	public static final InfoProperty AsymmetricObjectProperty = new InfoProperty(
			0x00);
	/**
	 * DisjointObjectProperties( :hasParent :hasSpouse )
	 */
	public static final InfoProperty DisjointObjectProperties = new InfoProperty(
			0x00);
	/**
	 * ReflexiveObjectProperty( :hasRelative )
	 */
	public static final InfoProperty ReflexiveObjectProperty = new InfoProperty(
			0x00);
	/**
	 * IrreflexiveObjectProperty( :parentOf )
	 */
	public static final InfoProperty IrreflexiveObjectProperty = new InfoProperty(
			0x00);
	/**
	 * FunctionalObjectProperty( :hasHusband )
	 */
	public static final InfoProperty FunctionalObjectProperty = new InfoProperty(
			0x00);
	/**
	 * InverseFunctionalObjectProperty( :hasHusband )
	 */
	public static final InfoProperty InverseFunctionalObjectProperty = new InfoProperty(
			0x00);
	/**
	 * TransitiveObjectProperty( :hasAncestor )
	 */
	public static final InfoProperty TransitiveObjectProperty = new InfoProperty(
			0x00);
	/**
	 * SubObjectPropertyOf( ObjectPropertyChain( :hasParent :hasParent )
	 * :hasGrandparent )
	 */
	public static final InfoProperty ObjectPropertyChain = new InfoProperty(
			0x00);
	/**
	 * HasKey( :Person () ( :hasSSN ) )
	 */
	public static final InfoProperty HasKey = new InfoProperty(0x00);
	/**
	 * DatatypeDefinition( :personAge DatatypeRestriction( xsd:integer
	 * xsd:minInclusive "0"^^xsd:integer xsd:maxInclusive "150"^^xsd:integer ) )
	 */
	public static final InfoProperty DatatypeDefinition = new InfoProperty(0x00);
	public static final InfoProperty DatatypeRestriction = new InfoProperty(
			0x00);
	/**
	 * DatatypeDefinition( :majorAge DataIntersectionOf( :personAge
	 * DataComplementOf( :minorAge ) ) )
	 */
	public static final InfoProperty DataIntersectionOf = new InfoProperty(0x00);
	/**
	 * DatatypeDefinition( :toddlerAge DataOneOf( "1"^^xsd:integer
	 * "2"^^xsd:integer ) )
	 */
	public static final InfoProperty DataOneOf = new InfoProperty(0x00);
	/**
	 * FunctionalDataProperty( :hasAge )
	 */
	public static final InfoProperty FunctionalDataProperty = new InfoProperty(
			0x00);
	/**
	 * SubClassOf( :Teenager DataSomeValuesFrom( :hasAge DatatypeRestriction(
	 * xsd:integer xsd:minExclusive "12"^^xsd:integer xsd:maxInclusive
	 * "19"^^xsd:integer ) ) )
	 */
	public static final InfoProperty DataSomeValuesFrom = new InfoProperty(0x00);
	/**
	 * SubClassOf( Annotation( rdfs:comment "States that every man is a person."
	 * ) :Man :Person )
	 */
	public static final InfoProperty Annotation = new InfoProperty(0x00);
	/**
	 * SameIndividual( :John otherOnt:JohnBrown ) SameIndividual( :Mary
	 * otherOnt:MaryBrown ) EquivalentClasses( :Adult otherOnt:Grownup )
	 * EquivalentObjectProperties( :hasChild otherOnt:child )
	 * EquivalentDataProperties( :hasAge otherOnt:age )
	 */
	public static final InfoProperty EquivalentObjectProperties = new InfoProperty(
			0x00);
	public static final InfoProperty EquivalentDataProperties = new InfoProperty(
			0x00);
	/**
	 * Declaration( NamedIndividual( :John ) ) Declaration( Class( :Person ) )
	 * Declaration( ObjectProperty( :hasWife ) ) Declaration( DataProperty(
	 * :hasAge ) )
	 */
	public static final InfoProperty Declaration = new InfoProperty(0x00);
}
