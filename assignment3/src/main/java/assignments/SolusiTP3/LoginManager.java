package assignments.SolusiTP3;

import assignments.systemCLI.AdminSystemCLI;
import assignments.systemCLI.CustomerSystemCLI;
import assignments.systemCLI.UserSystemCLI;

public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;

    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    // TODO: Solve the error :) (It's actually easy if you have done the other
    // TODOs)
    public UserSystemCLI getSystem(String role) {
        if (role == "Customer") {
            return customerSystem;
        } else {
            return adminSystem;
        }
    }
}
