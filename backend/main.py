from fastapi import FastAPI
from pydantic import BaseModel
import uvicorn

app = FastAPI()

# Define Data Models
class ChainRequest(BaseModel):
    user_input: str

class ChainResponse(BaseModel):
    step_1_output: str
    step_2_output: str
    final_result: str
    status: str

# --- The Sequential Chain Logic ---

def step_1_preprocess(text: str) -> str:
    # Example: Clean the text
    return text.strip().capitalize()

def step_2_analyze(text: str) -> str:
    # Example: Transform based on logic
    if len(text) > 5:
        return f"Analysis: '{text}' is a long string."
    return f"Analysis: '{text}' is a short string."

def step_3_finalize(step1: str, step2: str) -> str:
    # Combine results
    return f"FINAL REPORT -> Processed: {step1} | Result: {step2}"

# --- API Endpoint ---

@app.post("/process_chain", response_model=ChainResponse)
async def run_chain(request: ChainRequest):
    # 1. Input
    raw_input = request.user_input

    # 2. Transformation Step 1
    s1_out = step_1_preprocess(raw_input)

    # 3. Transformation Step 2
    s2_out = step_2_analyze(s1_out)

    # 4. Final Structured Output
    final_out = step_3_finalize(s1_out, s2_out)

    return ChainResponse(
        step_1_output=s1_out,
        step_2_output=s2_out,
        final_result=final_out,
        status="Success"
    )

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)