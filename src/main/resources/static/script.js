document.addEventListener("DOMContentLoaded", () => {
    // --- Default Code Template ---
    const defaultCode = `public class Main {
    public static void main(String[] args){
        System.out.println("Hello World!");
    }
}`;

    // --- CodeMirror Setup ---
    const editor = CodeMirror.fromTextArea(document.getElementById('code'), {
        mode: "text/x-java",
        theme: "dracula",
        lineNumbers: true,
        indentUnit: 4
    });

    // Load saved code if available, else default
    const savedCode = localStorage.getItem("savedCode");
    if (savedCode) {
        editor.setValue(savedCode);
    } else {
        editor.setValue(defaultCode);
    }

    // --- Elements ---
    const output = document.getElementById('output');
    const inputBox = document.getElementById('console-input');
    const ws = new WebSocket("ws://localhost:8080/ws/compiler");

    // --- Helper: Append text to console ---
    function appendToConsole(text) {
        output.textContent += text + "\n";
        output.scrollTop = output.scrollHeight;
    }

    // --- WebSocket Messages ---
    ws.onmessage = (event) => {
        appendToConsole(event.data);
    };

    // --- Run Button ---
    document.getElementById('run-btn').addEventListener('click', () => {
        output.textContent = "";
        const code = editor.getValue();
        ws.send("RUN:" + code);
    });

    inputBox.addEventListener('keydown', (e) => {
        if (e.key === "Enter" && !e.shiftKey) { // Shift+Enter = new line, Enter = send
            e.preventDefault();
            const value = inputBox.value.trim();
            if (value) {
                appendToConsole("> " + value);
                ws.send("INPUT:" + value);
            }
            inputBox.value = "";
        }
    });


    // --- Custom Confirmation Modal for Reset ---
    const resetBtn = document.getElementById('reset-btn');

    resetBtn.addEventListener('click', () => {
        // Create modal
        const modal = document.createElement("div");
        modal.classList.add("modal-overlay");
        modal.innerHTML = `
        <div class="modal-box">
            <h2>CompileX says</h2>
            <p>Are you sure you want to reset the code?</p>
            <div class="modal-actions">
                <button id="confirm-reset" class="confirm">Yes, Reset</button>
                <button id="cancel-reset" class="cancel">Cancel</button>
            </div>
        </div>
    `;
        document.body.appendChild(modal);

        // Handle buttons
        document.getElementById("confirm-reset").onclick = () => {
            editor.setValue(defaultCode);
            localStorage.removeItem("savedCode");
            modal.remove();
        };
        document.getElementById("cancel-reset").onclick = () => {
            modal.remove();
        };
    });

    // --- Save Button (localStorage) ---
    const saveBtn = document.querySelector('.nav-icons button[title="Save Code"]');
    saveBtn.addEventListener('click', () => {
        localStorage.setItem("savedCode", editor.getValue());
        alert("Code saved locally!");
    });

    // --- Download Button ---
    const downloadBtn = document.querySelector('.nav-icons button[title="Download Code"]');
    downloadBtn.addEventListener('click', () => {
        const blob = new Blob([editor.getValue()], { type: "text/plain" });
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "Main.java";
        link.click();
    });

    // --- Profile Button ---
    const profileBtn = document.querySelector('.profile-pic');
    profileBtn.addEventListener('click', () => {
        alert("Profile options will go here (Edit Profile, Change Password, Logout).");
    });
});
