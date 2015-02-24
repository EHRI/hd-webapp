EHRI Helpdesk Service
=========

Black-box servlet service that attempts to match user queries to specialist institutions
 based on their holdings.
 
Responds to `/ask` with a list of all available institutions, and the `/ask?q=Some query`
with an ordered map of institution IDs vs. confidence score.
