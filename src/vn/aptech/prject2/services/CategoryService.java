/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.prject2.services;

import javax.persistence.EntityManager;
import vn.aptech.entities.Category;
import vn.aptech.project2.utils.JPAUtils;

public class CategoryService extends AbstractFacade<Category>{

    
    public CategoryService() {
        super(Category.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return JPAUtils.getInstance().getEntityManager();
    }

}
