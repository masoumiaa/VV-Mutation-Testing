package m2ila.vv.Mutation_Testing;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class MethodBodyMutator {

	private CtClass ctClass;
	private CtMethod ctMethod;
	private ClassLoader clo;
	
	public void removeBodyMutation() throws NotFoundException, CannotCompileException, IOException{
		// Load class 
		this.loadClass();
		// Load method
		this.ctMethod = clo.getMethodByName(this.ctClass, "method1");
		// Remove method body
		this.removeMethodBody();
		// TODO this.runTests()
	}
	
	public void replaceBodyToFalseMutation() throws NotFoundException, CannotCompileException, IOException{
		// Load class 
		this.loadClass();
		// Load method
		this.ctMethod = clo.getMethodByName(this.ctClass, "method2");
		// Replace method body by (return false)
		this.replaceMethodBodyToFalse();
		// TODO this.runTests()
	}

	private void loadClass() throws NotFoundException, CannotCompileException{
		// Load classes
		this.clo = new ClassLoader();
		ClassPool pool = clo.loadClasses();
		// Get method class
		this.ctClass = clo.getCtClass(pool, "MethodOperations");
	}
	
	private void replaceMethodBodyToFalse() throws CannotCompileException, IOException {
		//Replace method body by (return false) 
		this.ctMethod.setBody("return false;");	
		
		// write copy class
		ctClass.writeFile("output");
		// now modifiable again
		ctClass.defrost();
		
		// TODO replace prints with reporting
		System.out.println("Method Body Replaced by (return false;)");
		
		
	}
	
	private void removeMethodBody() throws NotFoundException, CannotCompileException, IOException{
		//Remove method body 
		this.ctMethod.setBody("return;");		

		// write copy class
		ctClass.writeFile("output");
		// now modifiable again
		ctClass.defrost();
		
		// TODO replace prints with reporting
		System.out.println("Method Body Removed");
		
		//Getting all methodes 
		/*////////////// TODO move this code
		 * final CtMethod[] existingMethods = ctClass.getDeclaredMethods();
		for (CtMethod ctMethod : existingMethods) {
		
			// Substituting method body 
			ctMethod.instrument(new ExprEditor() {
				public void edit(NewExpr expr) throws CannotCompileException{
					if (expr.getClass().equals("+"))
						System.out.println("Found +");
				}
			});
		}*/
	}
}