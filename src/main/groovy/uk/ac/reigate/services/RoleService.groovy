package uk.ac.reigate.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uk.ac.reigate.domain.security.Role
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.security.RoleRepository
import uk.ac.reigate.utils.ValidationUtils

@Service
class RoleService implements ICoreDataService<Role, Integer>{
    
    @Autowired
    RoleRepository roleRepository
    
    /**
     * Default NoArgs constructor
     */
    RoleService() {}
    
    /**
     * Autowired Constructor
     *
     * @param roleRepository
     */
    RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    /**
     * Find an individual role using the roles ID fields
     *
     * @param id the ID fields to search for
     * @return the Role object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    Role findById(Integer id) {
        return roleRepository.findOne(id);
    }
    
    /**
     * Find all roles
     *
     * @return a SearchResult set with the list of Roles
     */
    @Override
    @Transactional(readOnly = true)
    List<Role> findAll() {
        return roleRepository.findAll();
    }
    
    /**
     * @param id
     * @param roleName
     * @param description
     * @return
     */
    @Transactional
    public Role saveRole(Integer id, String roleName) {
        ValidationUtils.assertNotBlank(roleName, "roleName cannot be blank");
        
        Role role = null;
        
        if (id != null) {
            role = findById(id);
            
            role.setRoleName(roleName);
            
            save(role);
        } else {
            role = save(new Role(roleName));
        }
        
        return role;
    }
    
    /**
     * This service method is used to save a complete Role object in the database
     *
     * @param role the new Role object to be saved
     * @return the saved version of the Role object
     */
    @Override
    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role)
    }
    
    /**
     * This service method is used to update an Role object in the database from a partial or complete Role object.
     *
     * @param role the partial or complete Role object to be saved
     * @return the saved version of the Role object
     */
    @Transactional
    public Role updateRole(Role role) {
        Role roleToSave = findById(role.id);
        roleToSave.roleName = role.roleName != null ? role.roleName : roleToSave.roleName
        return save(roleToSave)
    }
    
    /**
     * Saves a list of Role objects to the database
     *
     * @param roles a list of Roles to be saved to the database
     * @return the list of save Role objects
     */
    @Transactional
    public List<Role> saveRoles(List<Role> roles) {
        return roles.collect { role -> save(role) };
    }
    
    /**
     * This methods throws an InvalidOperationException when called. Role should not be deleted.
     */
    @Override
    public void delete(Role obj) {
        throw new InvalidOperationException("Role should not be deleted")
    }
}
